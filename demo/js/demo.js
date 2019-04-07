(function () {
    'use strict';
    if (window.Demo) {
        return;
    }

    var func = {
        init: function () {
            window.LoadedData = [];
            func.initDropdownList();
            func.initButtons();
            func.initGrid();
        },
        initButtons: function () {
            $("#btn_search").jqxButton();
            $("#btn_search").click(function () {
                var conOne = $("#ipt_condition_one").val();
                var conTwo = $("#ipt_condition_two").val();
                var result = window.LoadedData;
                if (conOne != "") {
                    var tempData = [];
                    // var conKv = conOne.split("=");
                    var conKv = conOne;
                    // if (conKv.length > 1) {
                        $(result).each(function (i, d) {
                            if (d["A"] == conKv) {
                                tempData.push(d);
                            }
                        });
                    // }
                    result = tempData;
                }

                if (conTwo != "") {
                    var tempData = [];
                    // var conKv = conTwo.split("=");
                    var conKv = conTwo;
                    // if (conKv.length > 1) {
                        $(result).each(function (i, d) {
                            if (d["B"] == conKv) {
                                tempData.push(d);
                            }
                        });
                    // }
                    result = tempData;
                }

                $("#grd_data").data("jqxGrid").instance.source._source.localdata = result;
                $("#grd_data").jqxGrid("updatebounddata");
            });

            $("#btn_submit").jqxButton();
            $("#btn_submit").click(function () {
                var cacheData = $("#grd_data").data("jqxGrid").instance.source.cachedrecords;
                $(cacheData).each(function (i, d) {
                    for (var i = 0; i < window.LoadedData.length; i++) {
                        if (window.LoadedData[i].id == d.id) {
                            window.LoadedData[i].A = d.A;
                            window.LoadedData[i].B = d.B;
                            window.LoadedData[i].C = d.C;
                            window.LoadedData[i].D = d.D;
                            break;
                        }
                    }
                });

            });

            $('#btn_upload').jqxFileUpload({ width: 300, uploadUrl: "#", fileInputName: 'fileToUpload' });
            $('#btn_upload').on('uploadEnd', function (event) {
                $("#ddl_filelist").jqxDropDownList('addItem', event.args.file);
                window.LoadedData = MockData;
                $("#grd_data").data("jqxGrid").instance.source._source.localdata = window.LoadedData || [];
                $("#grd_data").jqxGrid("updatebounddata");
            });

            $("#btn_export").jqxButton();
            $("#btn_export").click(function () {
                $("#grd_data").jqxGrid('exportdata', 'xls', 'jqxGrid', false);
            });
        },
        initDropdownList: function () {
            $("#ddl_filelist").jqxDropDownList({ source: [], placeHolder: "Select File", width: 250, height: 40 });
        },
        initGrid: function () {
            var numberrenderer = function (row, column, value) {
                return '<div style="text-align: center; margin-top: 5px;">' + (1 + value) + '</div>';
            }
            // create Grid datafields and columns arrays.
            var datafields = [{ name: "id" }];
            var columns = [];
            for (var i = 0; i < 4; i++) {
                var text = String.fromCharCode(65 + i);
                if (i == 0) {
                    columns[columns.length] = { pinned: true, exportable: false, text: "", columntype: 'number', cellsrenderer: numberrenderer };
                }
                datafields[datafields.length] = { name: text };
                columns[columns.length] = { text: text, datafield: text, width: '24%', align: 'center' };
            }
            var source =
            {
                datafields: datafields
            };
            var dataAdapter = new $.jqx.dataAdapter(source);
            // initialize jqxGrid
            $("#grd_data").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    editable: true,
                    columnsresize: true,
                    selectionmode: 'multiplecellsadvanced',
                    columns: columns
                });
        }
    };

    window.Demo = window.Demo || {
        init: func.init
    };

})();

$(document).ready(function () {
    window.Demo.init();
})