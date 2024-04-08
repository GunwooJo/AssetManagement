package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import kangnamuniv.assetmanagement.entity.AccountCurrency;
import kangnamuniv.assetmanagement.entity.Stock;
import kangnamuniv.assetmanagement.entity.StockAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class StockRepository {

    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<Stock> findByItemName(String itemName) {
        return em.createQuery("select s from Stock s where s.itemName = :itemName", Stock.class)
                .setParameter("itemName", itemName)
                .getResultList();
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

        //계좌에 특정 주식 이름으로 된 stock이 있는지 검색.
        Optional<Stock> foundStock = stockAccount.getStockList().stream()
                .filter(stock -> stock.getStockAccount().equals(stockAccount) && stock.getItemName().equals(itemName))
                .findFirst();

        //주식이 존재하면 업데이트
        if (foundStock.isPresent()) {
            Stock stock = foundStock.get();
            stock.setValuationPl(new BigDecimal(valutaionPl));
            stock.setValuationAmt(new BigDecimal(valuationAmt));
            stock.setQuantity(quantity);
            stock.setPurchaseAmount(new BigDecimal(purchaseAmount));
            stock.setEarningsRate(new BigDecimal(earningsRate));
            stock.setAccountCurrency(accountCurrency);
            em.merge(stock);
        } else {    //주식이 없으면 생성 후 저장

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
}
