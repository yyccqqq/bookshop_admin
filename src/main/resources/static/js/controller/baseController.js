app.controller("baseController", function ($scope) {
  $scope.reload = true;
  $scope.paginationConf = {
    currentPage: 1, //当前页数据  aglr控制
    totalItems: 10, //默认总条数 后台返回
    itemsPerPage: 10, //每页支持查询的条数 ag 前端控制
    perPageOptions: [10, 20, 30, 40, 50], //每页条数可选项
    onChange: function () {
      $scope.selectIds = [];
      if (!$scope.reload) {
        return;
      }
      $scope.reloadList(); //重新加载  这个方法会重复调用两次
      $scope.reload = false;
      setTimeout(function () {
        $scope.reload = true;
      }, 200);
    },
  };

  $scope.searchEntity = {};

  $scope.reloadList = function () {
    var pageNum = $scope.paginationConf.currentPage;
    var pageSize = $scope.paginationConf.itemsPerPage;
    $scope.search(pageNum, pageSize, $scope.searchEntity);
  };

  $scope.selectIds = [];

  $scope.updateSelection = function ($event, id) {
    if ($event.target.checked) {
      $scope.selectIds.push(id);
      angular.forEach($scope.list, function (value) {
        if (value.id == id) {
          value.select = true;
        }
      });
      $scope.checkSelectLength();
    } else {
      angular.forEach($scope.list, function (value) {
        if (value.id == id) {
          value.select = false;
        }
      });
      var index = $scope.selectIds.indexOf(id);
      $scope.selectIds.splice(index, 1);
      $scope.checkSelectLength();
    }
  };

   //全选
   $scope.selectAll = function ($event) {
    if ($event.target.checked) {
      $scope.selectIds = [];
      $scope.allSelect = true;
      angular.forEach($scope.list, function (value) {
        value.select = true;
        $scope.selectIds.push(value.id);
      });
    } else {
      $scope.allSelect = false;
      angular.forEach($scope.list, function (value) {
        value.select = false;
        $scope.selectIds = [];
      });
    }
  };

  $scope.checkSelectLength = function () {
    if ($scope.list.length == $scope.selectIds.length) {
      $scope.allSelect = true;
    } else {
      $scope.allSelect = false;
    }
  };
});

layui.use("layer", function () {
  var layer = layui.layer;
});
