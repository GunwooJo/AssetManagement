package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.dto.StockDTO;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Stock;
import kangnamuniv.assetmanagement.entity.StockAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StockRepository {

    private final EntityManager em;

    @Transactional(readOnly = true)
    public Stock findByItemName(String itemName) {
        return em.createQuery("select s from Stock s where s.itemName = :itemName", Stock.class)
                .setParameter("itemName", itemName)
                .getSingleResult();
    }

    public Stock addStock(StockAccount stockAccount, String itemName, String valutaionPl, String valuationAmt, Long quantity, String purchaseAmount, String earningsRate, AccountCurrency accountCurrency) {
        Stock stock = Stock.builder()
                .itemName(itemName)
                .valuationPl(new BigDecimal(valutaionPl))
                .valuationAmt(new BigDecimal(valuationAmt))
                .quantity(quantity)
                .purchaseAmount(new BigDecimal(purchaseAmount))
                .earningsRate(new BigDecimal(earningsRate))
                .accountCurrency(accountCurrency)
                .build();

        stock.setStockAccount(stockAccount);
        em.persist(stock);
        return stock;
    }

    //stockAccount와 itemName으로 Stock 조회해서 값 수정.
    public Stock modifyStock(StockAccount stockAccount, String itemName, String valutaionPl, String valuationAmt, Long quantity, String purchaseAmount, String earningsRate, AccountCurrency accountCurrency) {
        Stock resStock = em.createQuery("select s from Stock s where s.itemName = :itemName and s.stockAccount = :stockAccount", Stock.class)
                .setParameter("itemName", itemName)
                .setParameter("stockAccount", stockAccount)
                .getSingleResult();

        resStock.setValuationPl(new BigDecimal(valutaionPl));
        resStock.setValuationAmt(new BigDecimal(valuationAmt));
        resStock.setQuantity(quantity);
        resStock.setPurchaseAmount(new BigDecimal(purchaseAmount));
        resStock.setEarningsRate(new BigDecimal(earningsRate));
        resStock.setAccountCurrency(accountCurrency);

        return resStock;
    }

    public void addOrUpdateStock(StockAccount stockAccount, String itemName, String valutaionPl, String valuationAmt, Long quantity, String purchaseAmount, String earningsRate, AccountCurrency accountCurrency) {
        log.debug("함수 호출: addOrUpdateStock");
        log.debug("평가금액 파라미터: {}", valuationAmt);
        //계좌에 특정 주식 이름으로 된 stock이 있는지 검색.
        Optional<Stock> foundStock = stockAccount.getStockList().stream()
                .filter(stock -> stock.getStockAccount().equals(stockAccount) && stock.getItemName().equals(itemName))
                .findFirst();

        //주식이 존재하면 업데이트
        if (foundStock.isPresent()) {
            log.debug("주식이 존재하면 업데이트");
            Stock stock = foundStock.get();
            stock.setValuationPl(new BigDecimal(valutaionPl));
            stock.setValuationAmt(new BigDecimal(valuationAmt));
            stock.setQuantity(quantity);
            stock.setPurchaseAmount(new BigDecimal(purchaseAmount));
            stock.setEarningsRate(new BigDecimal(earningsRate));
            stock.setAccountCurrency(accountCurrency);
        } else {    //주식이 없으면 생성 후 저장
            log.debug("주식이 없으면 생성 후 저장");
            Stock newStock = new Stock();
            newStock.setStockAccount(stockAccount);
            newStock.setItemName(itemName);
            newStock.setValuationPl(new BigDecimal(valutaionPl));
            newStock.setValuationAmt(new BigDecimal(valuationAmt));
            newStock.setQuantity(quantity);
            newStock.setPurchaseAmount(new BigDecimal(purchaseAmount));
            newStock.setEarningsRate(new BigDecimal(earningsRate));
            newStock.setAccountCurrency(accountCurrency);
            em.persist(newStock);
        }
    }

    public void updateStock(StockAccount stockAccount, List<StockDTO> newStocks) {

        List<Stock> existingStocks = stockAccount.getStockList();
        List<String> newStockNames = new ArrayList<>();

        for (StockDTO newStock : newStocks) {
            newStockNames.add(newStock.getItemName());
        }

        // 기존 종목 중 새로운 종목에 포함되지 않은 종목 삭제
        for (Stock existingStock : existingStocks) {
            if(!newStockNames.contains(existingStock.getItemName())) {
                em.remove(existingStock);
            }
        }

        // 새로운 종목 추가 또는 업데이트
        for (StockDTO newStock : newStocks) {
            Stock foundStock = findByItemName(newStock.getItemName());

            if (foundStock == null) {

                Stock stock = Stock.builder()
                        .purchaseAmount(newStock.getPurchaseAmount())
                        .quantity(newStock.getQuantity())
                        .valuationAmt(newStock.getValuationAmt())
                        .valuationPl(newStock.getValuationPl())
                        .itemName(newStock.getItemName())
                        .accountCurrency(newStock.getAccountCurrency())
                        .stockAccount(stockAccount)
                        .earningsRate(newStock.getEarningsRate())
                        .build();

                em.persist(stock);

            } else {
                foundStock.setQuantity(newStock.getQuantity());
                foundStock.setPurchaseAmount(newStock.getPurchaseAmount());
                foundStock.setValuationAmt(newStock.getValuationAmt());
                foundStock.setValuationPl(newStock.getValuationPl());
                foundStock.setEarningsRate(newStock.getEarningsRate());
                em.merge(foundStock);
            }
        }
    }
}
