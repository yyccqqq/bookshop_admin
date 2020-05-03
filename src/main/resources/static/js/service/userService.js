app.service("userService", function ($http) {
  //搜索
  this.search = function (page, rows, searchEntity) {
    return $http.post("../user/search/" + page + "/" + rows, searchEntity);
  };

  //查询实体
  this.findOne = function (id) {
    return $http.get("../user/findOne/" + id);
  };

  //删除实体
  this.deleteOne = function (id) {
    return $http.get("../user/deleteOne/" + id);
  };

  this.delete = function (ids) {
    return $http.post("../user/delete", ids);
  };
});
