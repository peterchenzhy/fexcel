(window.webpackJsonp=window.webpackJsonp||[]).push([[4],{615:function(e,t,a){},819:function(e,t,a){"use strict";a.r(t);a(537);var l=a(568),n=(a(815),a(810)),o=(a(598),a(720)),i=(a(816),a(812)),r=(a(484),a(483)),s=(a(539),a(535)),c=(a(603),a(717)),p=(a(604),a(814)),u=a(125),m=a(167),d=a(606),h=a(607),f=a(817),g=a(608),b=a(818),F=(a(609),a(813)),w=(a(611),a(712)),S=(a(613),a(811)),y=a(6),E=a.n(y),v=(a(615),a(118)),C=(a(616),a(59)),L=a(28),k=a(52),x=(a(127),S.a.Item),N=w.a.Content,O=F.a.info,j=F.a.error,I=F.a.success,P={labelCol:{span:6},wrapperCol:{span:18}},T={md:24,lg:12,xl:8},V=function(e){function t(e){var a;return Object(d.a)(this,t),(a=Object(f.a)(this,Object(g.a)(t).call(this,e))).generateForm=function(e){var t=a.props.form,l=(t.getFieldDecorator,t.setFieldsValue),n=[],o=[];e.forEach(function(e){1===e.canSearch&&(n.push(e.tableCollumName),o.push(e))}),a.setState({fields:Object(m.a)(new Set(n))}),l({keys:o})},a.loadPage=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50;a.setState({pageNo:e},function(){a.props.form.validateFields(function(l,n){if(!l){var o=a.state.fields,i={};i.pageNo=e,i.pageSize=t,i.tableName=n.fileId,o.forEach(function(e){n[e]&&""!==n[e]&&(i[e]=n[e])}),a.props.actions.getTableList(i)}})})},a.handlePageChange=function(e,t){a.loadPage(e,t)},a.handleShowSizeChange=function(e,t){a.loadPage(1,t)},a.handleSearch=function(e){e&&e.preventDefault(),a.loadPage()},a.batchUpdate=function(){var e=a.state.selectedRows,t=a.props.form.getFieldValue;a.props.actions.batchUpdate({list:e,tableName:t("fileId")})},a.showSuccess=function(e){I({title:"\u64cd\u4f5c\u6210\u529f",content:e,okText:"\u786e\u5b9a",okType:"info",onOk:function(){a.loadPage()}})},a.showError=function(e){j({title:"\u64cd\u4f5c\u5931\u8d25",content:e,okText:"\u786e\u5b9a",okType:"info"})},a.showInfo=function(e){O({title:"\u64cd\u4f5c\u63d0\u793a",content:e,okText:"\u786e\u5b9a",okType:"info"})},a.exportFile=function(){var e=(0,a.props.form.getFieldValue)("fileId");e&&""!==e?window.open("".concat(k.a.config.export,"?tableName=").concat(e)):a.showError("\u8bf7\u5148\u9009\u62e9\u8981\u5bfc\u51fa\u7684\u6587\u4ef6!")},a.changeSelectGroup=function(e){e&&""!==e&&a.props.actions.getColumnList({tableName:e})},a.getFileList=function(){a.props.actions.getFileList()},a.handleShowModal=function(e){var t=e.modalVar,l=e.flag,n=e.cb;a.setState(Object(u.a)({},t,l)),n&&n()},a.handleFieldsBlur=function(e){var t=e.key,l=e.value;(0,a.props.form.setFieldsValue)(Object(u.a)({},t,l))},a.handleBlur=function(e){var t=e.key,l=e.value,n=e.formKey,o=e.id;(0,a.props.form.setFieldsValue)(Object(u.a)({},n,l)),a.props.actions.editData({key:t,value:l,id:o})},a.resetForm=function(){a.props.form.resetFields()},a.state={isShowModal:!1,fileLoading:!1,confirmLoading:!1,selectedRowKeys:[],selectedRows:[],formSearch:[],fields:[]},a}return Object(b.a)(t,e),Object(h.a)(t,[{key:"componentDidMount",value:function(){this.getFileList()}},{key:"componentWillReceiveProps",value:function(e){this.props.refreshFileFlag!==e.refreshFileFlag&&e.refreshFileFlag&&(this.getFileList(),this.props.actions.resetState()),this.props.columnList!==e.columnList&&e.columnList.length>0&&this.generateForm(e.columnList),this.props.operateSuccessFlag!==e.operateSuccessFlag&&!0===e.operateSuccessFlag&&(this.showSuccess(e.operateInfo),this.props.actions.resetState()),this.props.operateFailFlag!==e.operateFailFlag&&!0===e.operateFailFlag&&(this.showError(e.operateInfo),this.props.actions.resetState())}},{key:"render",value:function(){var e=this,t=this,a=this.props.form,u=a.getFieldDecorator,m=a.getFieldValue;a.getFieldProps;console.log(m("keys"));var d=this.props,h=d.columnList,f=d.tableList,g=d.fileList,b=d.loading,w=this.state,y=w.isShowModal,v=w.confirmLoading,C=w.fileLoading,L=w.selectedRowKeys,k=(w.formSearch,this.props.pageInfo),O=k.pageNum,j=k.total,I=k.pageSize,V=h&&h.length>0?h.filter(function(e){return 1===e.canView}).map(function(t){return{title:t.excelCollumName,key:t.tableCollumName,render:function(a,l){return 1===t.canEdit?E.a.createElement(x,{label:""},u("".concat(t.tableCollumName,"_").concat(l.id),{initialValue:l[t.tableCollumName],onChange:function(a){return e.handleBlur({id:l.id,formKey:"".concat(t.tableCollumName,"_").concat(l.id),value:a.target.value,key:t.tableCollumName})}})(E.a.createElement(p.a,{placeholder:"\u8bf7\u8f93\u5165"}))):E.a.createElement("span",null,l[t.tableCollumName])}}}):[],R={current:O,total:j,pageSize:I,showSizeChanger:!0,showQuickJumper:!0,onChange:this.handlePageChange,onShowSizeChange:this.handleShowSizeChange},z={name:"file",beforeUpload:function(e,t){},showUploadList:!1,customRequest:function(e){var a=new FormData;a.append("excelFile",e.file),t.props.actions.uploadFile(a)}},B={onChange:function(t,a){e.setState({selectedRowKeys:t,selectedRows:a})},getCheckboxProps:function(e){return{disabled:!1,name:e.name}}};u("keys",{initialValue:[]});var D=m("keys").map(function(t){return E.a.createElement(c.a,T,E.a.createElement(x,Object.assign({},P,{label:"".concat(t.excelCollumName)}),u("".concat(t.tableCollumName),{initialValue:"",onChange:function(a){return e.handleFieldsBlur({id:t.tableCollumName,value:a.target.value})}})(E.a.createElement(p.a,{placeholder:"\u8bf7\u8f93\u5165"}))))});return E.a.createElement("div",{className:"page-index"},E.a.createElement(l.a,{spinning:C},E.a.createElement(N,null,E.a.createElement(S.a,{className:"ant-advanced-search-form",onSubmit:this.handleSearch},E.a.createElement(o.a,{gutter:24},E.a.createElement("h3",null,"\u6587\u4ef6\u4e0a\u4f20"),E.a.createElement(c.a,{span:10,style:{height:70}},E.a.createElement(x,Object.assign({},P,{label:"\u6587\u4ef6"}),u("fileId",{rules:[{required:!0,whitespace:!0,message:"\u8bf7\u5148\u9009\u62e9\u6587\u4ef6"}]})(E.a.createElement(s.a,{showSearch:!0,optionFilterProp:"children",allowClear:!0,style:{width:400},placeholder:"\u8bf7\u9009\u62e9",onSelect:this.changeSelectGroup},g&&g.length>0&&g.map(function(e){return E.a.createElement(s.a.Option,{title:e.excelName,key:"tableFile_".concat(e.id),value:e.tableName},e.excelName)}))))),E.a.createElement(c.a,{span:8,style:{textAlign:"left"}},E.a.createElement(i.a,z,E.a.createElement(r.a,{type:"primary"},"\u4e0a\u4f20\u6587\u4ef6")),E.a.createElement(r.a,{style:{marginLeft:"10px"},type:"default",onClick:this.exportFile},"\u5bfc\u51fa")),E.a.createElement(c.a,{style:{clear:"both"}})),E.a.createElement(o.a,{gutter:24},D),E.a.createElement(o.a,{gutter:24},E.a.createElement(c.a,Object.assign({},T,{style:{marginLeft:"700px",marginBottom:"15px"}}),E.a.createElement(r.a,{type:"primary",htmlType:"submit"},"\u67e5\u8be2"),"\xa0\xa0\xa0\xa0",E.a.createElement(r.a,{type:"default",onClick:this.resetForm},"\u91cd\u7f6e")))),E.a.createElement("h3",{style:{margin:"0 0 5px",paddingTop:"25px",borderTop:"1px solid #ddd"}},"\u6587\u4ef6\u5bfc\u5165\u8bb0\u5f55"),E.a.createElement(o.a,{gutter:24,style:{marginBottom:"10px"}},E.a.createElement(c.a,{style:{textAlign:"left"},span:12},E.a.createElement(r.a,{type:"primary",onClick:this.batchUpdate,disabled:!L.length>0},"\u6279\u91cf\u66f4\u65b0")),E.a.createElement(c.a,{style:{textAlign:"right"},span:12},E.a.createElement("div",{className:"search-count"},"\u5171 ",R.total," \u6761"))),E.a.createElement(l.a,{spinning:b},E.a.createElement(n.a,{scroll:{x:"100%"},rowKey:"id",className:"table-list",rowSelection:B,dataSource:f,columns:V,pagination:R})),E.a.createElement(F.a,{title:"\u64cd\u4f5c",width:600,visible:y,confirmLoading:v,onCancel:function(){e.handleShowModal({modalVar:"isShowModal",flag:!1})},okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88"}))))}}]),t}(y.PureComponent),R=S.a.create()(V);t.default=Object(v.connect)(function(e){var t=e.resumeReducer;return{resultList:t.resultList,operateSuccessFlag:t.operateSuccessFlag,operateFailFlag:t.operateFailFlag,operateInfo:t.operateInfo,tableList:t.tableList,fileList:t.fileList,refreshFileFlag:t.refreshFileFlag,columnList:t.columnList,pageInfo:t.pageInfo,loading:t.loading}},function(e){return{actions:Object(C.bindActionCreators)(L.a,e)}})(R)}}]);
//# sourceMappingURL=4.edfa5cf4.chunk.js.map