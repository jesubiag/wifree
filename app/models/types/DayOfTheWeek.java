package models.types;

import io.ebean.annotation.EnumValue;

public enum DayOfTheWeek {
	
	@EnumValue("MONDAY") MONDAY,
	@EnumValue("TUESDAY") TUESDAY,
	@EnumValue("WEDNESDAY") WEDNESDAY,
	@EnumValue("THURSDAY") THURSDAY,
	@EnumValue("FRIDAY") FRIDAY,
	@EnumValue("SATURDAY") SATURDAY,
	@EnumValue("SUNDAY") SUNDAY,
	@EnumValue("NONE") NONE,
	@EnumValue("ALL") ALL,
	@EnumValue("ERROR") ERROR
	
}
