app.service("bookReviewService", function ($http) {
  //搜索
  this.search = function (page, rows, searchEntity) {
    return $http.get("../book/findBookReview/" + page + "/" + rows);
  };

  //查询实体
  this.findOne = function (id) {
    return $http.get("../book/findOne/" + id);
  };

  //批量审核
  this.update = function (ids, bookType) {
    return $http.put("../book/update/"+bookType, ids);
  };

  //审核
  this.updateOne = function (id,bookType) {
    return $http.get("../book/updateOne/" + id+"/"+bookType);
  };
});
