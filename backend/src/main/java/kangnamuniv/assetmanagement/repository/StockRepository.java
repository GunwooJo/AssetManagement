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

@Repository
@Transactional
@RequiredArgsConstructor
public class StockRepository {

    private final EntityManager em;

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
}
