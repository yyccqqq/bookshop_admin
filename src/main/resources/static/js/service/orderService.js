app.service("orderService", function ($http) {
  //搜索
  this.search = function (page, rows) {
    return $http.get("../orders/search/" + page + "/" + rows);
  };

  //查询实体
  this.findOne = function (id) {
    return $http.get("../orders/findOne/" + id);
  };

  //删除实体
  this.delete = function (ids) {
    return $http.post("../orders/delete", ids);
  };

  //获取周交易数据
  this.getData = function () {
    return $http.get("../orders/getData");
  };
});
