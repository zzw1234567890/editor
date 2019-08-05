function Data(){
	this.UserData = {
		//用户数据
	};
	this.Ajax = function(url,data=null,type='get'){
		var data;
		$.ajax({
			type:type,
			url:url,
			data:data,
			xhrFields:{
				withCredentials:true
			},
			async:false,
			success:function(e){
				data = e;
			},
			error: function(e){
				console.log(e);
			}
		});
		return data;
	}
	this.FileListData = function(){
		return this.Ajax("http://" + domain + '/editor/file/getFileList');
	}
	this.ReName = function(path,name){
		return this.Ajax("http://" + domain + '/editor/file/rename',{oldPath:path,newPath:name}).result;
	}
	this.DeletePath = function(path){
		return this.Ajax("http://" + domain + '/editor/file/del',{path:path}).result;
	}
	this.CreatePath = function(path){
		return this.Ajax("http://" + domain + '/editor/file/create',{path:path}).result;
	}
	this.OpenPath = function(path){
		return this.Ajax("http://" + domain + '/editor/file/getFileContent',{path:path}).content;
	}
	this.SavePath = function(path,data){
		return this.Ajax("http://" + domain + '/editor/file/update',{path:path,content:data},'post');
	}
	this.CopyPath = function(fromPath,toPath){
		return this.Ajax("http://" + domain + '/editor/file/copy',{fromPath:fromPath,toPath:toPath}).result;
	}
	this.IdTest = function(){
		return this.Ajax("http://" + domain + '/index/login',null);
	}
}

var Data = new Data();