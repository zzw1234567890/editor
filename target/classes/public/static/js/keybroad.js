var Key = new function Key(){
	this.Init = function(){
		var that = this;
		$(document).keydown(function(e){
			that[e.key.toUpperCase()] = true;
		});
		$(document).keyup(function(e){
			that[e.key.toUpperCase()] = false;
		});
	}
}
