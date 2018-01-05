package prizy_pricer

import com.grails.practical.Product
import com.grails.pricecalculation.strategy.map.StrategyMap

import grails.transaction.Transactional

@Transactional
class ProductService {
	StrategyMap strategyMapInstance = new StrategyMap();
	def priceService
	
    def getStandardStrategySet(){
		def standardStrategySet = strategyMapInstance.getStandardStrategySet()
		def set = new HashSet<String>()
		standardStrategySet.each {
			e -> set.add(e.toLowerCase().capitalize())
		}
		return set
	}

	def calculateForDefaultStrategies(String barcode){
		def strategyMap = strategyMapInstance.calculateForDefaultStrategies(priceService.getPriceList(barcode))
		def map = new HashMap<String, BigDecimal>()
		strategyMap.each {
			key,value -> map.put(String.valueOf(key).toLowerCase().capitalize(), value)
		}
		return map
	}
	
	
	def calculateForStandardStrategy(String barcode,String strategy){
		def price = strategyMapInstance.calculateForStandardStrategy(priceService.getPriceList(barcode),strategy.toUpperCase())
		return price
		}
	
	synchronized getProductCountForSearch(String text) {
		def criteria = Product.createCriteria()
		if(null == text)
			return 0
		List<Product> result = criteria.list(){
			like('barcode', "%"+text.trim()+"%")
			projections {
				count()
			}
		}
		return result[0]
	}
}
