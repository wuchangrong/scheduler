    
     function initial(currentPage,totalPage)
     {   
       if(currentPage == 1)
       {
          document.getElementById("firstButton").className="NEUDwButton_FirstPage_Disable";
          document.getElementById("firstButton").disabled=true;
          document.getElementById("upButton").className="NEUDwButton_PreviousPage_Disable";
          document.getElementById("upButton").disabled=true;
       }
       if(currentPage == totalPage)
       {
          document.getElementById("downButton").className="NEUDwButton_NextPage_Disable";
           document.getElementById("downButton").disabled=true;
          document.getElementById("lastButton").className="NEUDwButton_LastPage_Disable";
          document.getElementById("lastButton").disabled=true;
       }
      
     }  
     
     function jumpPage(method,currentPage,formId,actionName) 
     {
       
          formObj = document.getElementById(formId);
		  var pageNo = parseInt(formObj.pageNo.value);
		  var pageCount = parseInt(formObj.totalPage.value);  
		   
		  var n;
		  if (method == 'first') {
		    n = 1;
		  } else if (method == 'up') {
		    n = pageNo - 1;
		  } else if (method == 'down') {
		    n = pageNo + 1;
		  } else if (method == 'last') {
		    n = pageCount;
		  } else if (method == 'jump') {
		    
		  	if (formObj.pageNo.value === ""){
		  	    
		    	alert(SCHEDULARCONSTANTS.alert_pageNoIsNotNull);
		    	formObj.pageNo.value = currentPage;
		    	return;
		    }
		    
		    if (pageNo <= 0) {
		       formObj.pageNo.value = currentPage;
		       alert(SCHEDULARCONSTANTS.alert_pageNoIsNotLessZero);
		       return;
		    }
		    if (pageNo > pageCount) n = pageCount;
		    else if (pageNo < 1) n = 1;
		    else n = pageNo;
		  }	
		   //formObj.action = "displaTasks.do";
		   formObj.action = actionName;
		   formObj.target = "";
		   formObj.pageNo.value = n;
		  formObj.submit();
		  
}
