package operations.core

trait WiFreeResponse extends Operation {
	
	override def operationType: OperationType = OperationType.RESPONSE
	
	def responseType: ResponseType
}
