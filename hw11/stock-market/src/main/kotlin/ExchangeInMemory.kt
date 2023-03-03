import java.util.concurrent.ConcurrentHashMap

class ExchangeInMemory {
    private val stocks: MutableMap<String, Stock> = ConcurrentHashMap()

    fun addCompany(companyName: String, price: Double) {
        if (price <= 0) {
            throw MarketException("Stock price must be a positive number")
        }
        if (companyName in stocks) {
            throw MarketException("Company $companyName already exists")
        }
        stocks[companyName] = Stock(companyName, price, 0)
    }

    fun addStock(companyName: String, quantity: Int) {
        if (quantity < 0) {
            throw MarketException("Stock quantity must be a non-negative number")
        }
        val stock = getStocksInfo(companyName)
        stock.quantity += quantity
    }

    fun getStocks(): Map<String, Stock> {
        return stocks
    }

    fun getStocksInfo(companyName: String): Stock {
        return stocks[companyName] ?: throw MarketException("Company $companyName does not exist")
    }

    fun buyStocks(companyName: String, quantity: Int): Double {
        val stock = getStocksInfo(companyName)
        if (quantity > stock.quantity) {
            throw MarketException("There are no $quantity shares in stock, there are ${stock.quantity}")
        }

        stock.quantity -= quantity
        return stock.price * quantity
    }

    fun changeExchangeRate(companyName: String, price: Double) {
        if (price <= 0) {
            throw MarketException("Price must be a positive number")
        }
        val stock = getStocksInfo(companyName)
        stock.price = price
    }
}