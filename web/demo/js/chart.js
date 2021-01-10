function initChart(chartId, title , subTitle, value_1, value_2, backgroundColor){

    var myChart = echarts.init(document.getElementById(chartId));

    // 指定图表的配置项和数据

    var labelOption = {
        show: true,
        position: 'insideTop',
        distance: 0,
        align: 'middle',
        verticalAlign: 'middle',
        rotate: 0,
        formatter: 'CAD {c}  ',
        fontSize: 18,
        rich: {
            c: {
                textBorderColor: '#000000'
            }

        }
    };

    var option = {
        backgroundColor: backgroundColor,
        title: {
                text: title,
                subtext: subTitle,
                left: 'center'
            },
        legend: {show: false},
        tooltip: {},

        xAxis: {type: 'category',
            show:false,
            },
        yAxis: {show: false},
        series: [
            {type: 'bar',label: labelOption, name:'_',data: [value_1 ]},
            {type: 'bar',label: labelOption, name:' ',data: [value_2 ]}
        ]
    };
    myChart.setOption({
        backgroundColor: '#E0E5E5'
    })
    myChart.setOption(option);
}

