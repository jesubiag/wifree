function toJson(string) {
	var stringJson = string.replace(/&quot;/g, '"');
	return JSON.parse(stringJson);
}
