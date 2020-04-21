OB.PAPER = OB.PAPER || {};
 
OB.PAPER.Process = {
  execute: function (params, view) {
    var i, selection = params.button.contextView.viewGrid.getSelectedRecords(),
        orders = [],
        callback;
 
    callback = function (rpcResponse, data, rpcRequest) {
      // show result
      isc.say(OB.I18N.getLabel('paper_Updated', [data.updated]));
 
      // refresh the whole grid after executing the process
      params.button.contextView.viewGrid.refreshGrid();
    };
 
    for (i = 0; i < selection.length; i++) {
      orders.push(selection[i].id);
    };
 
    OB.RemoteCallManager.call('com.ibox.paper.ad_handler.ManualProcessActionHandler', {
      orders: orders,
      action: params.action
    }, {}, callback);
  },
 
  quality: function (params, view) {
    params.action = 'quality';
    OB.PAPER.Process.execute(params, view);
  },
  qualityReturn: function (params, view) {
	    params.action = 'qualityReturn';
	    OB.PAPER.Process.execute(params, view);
	  }
  
};