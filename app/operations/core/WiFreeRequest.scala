package operations.core

trait WiFreeRequest extends Operation {
	
	override def operationType: OperationType = OperationType.REQUEST
	
	def requestType: RequestType
	
}
