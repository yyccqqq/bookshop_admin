app.controller("orderController", function ($scope, $controller, orderService) {
  $controller("baseController", { $scope: $scope }); //继承

  //搜索
  $scope.search = function (page, rows) {
    orderService.search(page, rows).success(function (response) {
      $scope.list = response.list;
      $scope.paginationConf.totalItems = response.total; //更新总记录数
    });
  };

  //全选
  $scope.allSelect = function ($event) {
    if ($event.target.checked) {
      $scope.selectIds = [];
      $scope.allSelect = true;
      angular.forEach($scope.list, function (value) {
        value.select = true;
        $scope.selectIds.push(value.orderId);
      });
    } else {
      $scope.allSelect = false;
      angular.forEach($scope.list, function (value) {
        value.select = false;
        $scope.selectIds = [];
      });
    }
  };

  //查询实体
  $scope.findOne = function (id) {
    orderService.findOne(id).success(function (response) {
      $scope.entity = response;
    });
  };

  //删除实体
  $scope.delete = function () {
    if ($scope.selectIds.length > 0) {
      orderService.delete($scope.selectIds).success(function (response) {
        $scope.reloadList();
      });
    } else {
      layer.msg("请选择");
    }
  };

  //获取周交易数据
  $scope.getData = function () {
    orderService.getData().success(function (response) {
      var dataAxis = response.dateList;
      var data = response.dataList;
      $.setEcharts(dataAxis, data);
    });
  };
});

$.setEcharts = function (dataAxis, data) {
  var myChart = echarts.init(document.getElementById("main"));
  var yMax = 100;
  var dataShadow = [];

  for (var i = 0; i < data.length; i++) {
    dataShadow.push(yMax);
  }

  option = {
    title: {
      text: "校园二手书周交易数据",
      subtext: "单位：元",
    },
    xAxis: {
      data: dataAxis,
      axisLabel: {
        inside: true,
        textStyle: {
          color: "#fff",
        },
      },
      axisTick: {
        show: false,
      },
      axisLine: {
        show: false,
      },
      z: 10,
    },
    yAxis: {
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
      axisLabel: {
        textStyle: {
          color: "#999",
        },
      },
    },
    dataZoom: [
      {
        type: "inside",
      },
    ],
    series: [
      {
        // For shadow
        type: "bar",
        itemStyle: {
          color: "rgba(0,0,0,0.05)",
        },
        barGap: "-100%",
        barCategoryGap: "40%",
        data: dataShadow,
        animation: false,
      },
      {
        type: "bar",
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: "#83bff6" },
            { offset: 0.5, color: "#188df0" },
            { offset: 1, color: "#188df0" },
          ]),
        },
        emphasis: {
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: "#2378f7" },
              { offset: 0.7, color: "#2378f7" },
              { offset: 1, color: "#83bff6" },
            ]),
          },
        },
        data: data,
      },
    ],
  };

  var zoomSize = 6;
  myChart.on("click", function (params) {
    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
    myChart.dispatchAction({
      type: "dataZoom",
      startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
      endValue:
        dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)],
    });
  });

  myChart.setOption(option);
};
