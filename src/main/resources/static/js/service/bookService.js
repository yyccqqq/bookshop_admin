app.service('bookService', function ($http) {

    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../book/search/'+page+'/'+rows, searchEntity);
    }

    //查询实体
    this.findOne=function(id){
        return $http.get('../book/findOne/'+id);
    }

    //删除实体
    this.delete = function (ids) {
        return $http.post('../book/delete', ids);
    }

})