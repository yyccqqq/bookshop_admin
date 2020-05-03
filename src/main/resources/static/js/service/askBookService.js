app.service("askBookService", function ($http) {
  //搜索
  this.search = function (page, rows, searchEntity) {
    return $http.post("../askbook/search/" + page + "/" + rows, searchEntity);
  };

  //查询实体
  this.findOne = function (id) {
    return $http.get("../askbook/findOne/" + id);
  };

  //删除实体
  this.delete = function (ids) {
    return $http.post("../askbook/delete", ids);
  };
});
