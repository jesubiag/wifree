package views

import models.types.AccountType

object ViewsHelper {
	
	def accountTypes: Seq[(String, String)] = {
		AccountType.values().map(value => (value.toString, AccountType.valueOf(value.toString).name())).toSeq
	}
	
}
