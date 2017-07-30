function validateExpression(expression) {
    if(expression==null||expression==""){
    	return false;
    }
    var isvalid = isValidExpression(expression);
    if(isvalid){
    	return true;
    }
    else
     {
     	return false;
     }
 }

function isValidExpression(cronExpression)
 {
    if(cronExpression==null||cronExpression==""){
    	return false;
    }
    cronExpression = cronExpression.trim();
    var cronArray = cronExpression.split(" ");
    if(cronArray.length<6||cronArray.length>7){
       return false;
    }
    var temp = false;
    if((cronArray[3]=="?"&&cronArray[5]!="?")||(cronArray[5]=="?"&&cronArray[3]!="?")){
    	temp = true;
    }
    if(!temp){
    	return false;
    }
    for(var i=0;i<cronArray.length;i++){
    	var temp = validateExpressionVals(0,cronArray[i],i);
    	if(!temp){
    	    return false;
    	}
    }
    return true;
 }
function validateExpressionVals(pos,s,atype)
 {
    var s_Array = s.split(",");
    var pattern;
    var flag = false;
    //validate second and minute allowed special characters '*,/-'
    if(atype==0||atype==1){
      for(var i=0;i<s_Array.length;i++){
       var s_item = s_Array[i];
       pattern=/^((\*)|(\*\/(([1-9])|([1-5][0-9])))|(([0-9])|([1-5][0-9]))|((([0-9])|([1-5][0-9]))\/(([1-9])|([1-5][0-9])))|((([0-9])|([1-5][0-9]))-(([0-9])|([1-5][0-9])))|(((([0-9])|([1-5][0-9]))-(([0-9])|([1-5][0-9])))\/(([1-9])|([1-5][0-9]))))$/;
       flag=pattern.test(s_item);
       if(!flag){
         return false;
       }
      }
      return true;
    }
    //validate hour '*,/-'
    else if(atype==2)
     {
      for(var i=0;i<s_Array.length;i++){
       var s_item = s_Array[i];
       pattern=/^((\*)|(\*\/(([1-9])|([1][0-9])|([2][0-3])))|(([0-9])|([1][0-9])|([2][0-3]))|((([0-9])|([1][0-9])|([2][0-3]))\/(([1-9])|([1][0-9])|([2][0-3])))|((([0-9])|([1][0-9])|([2][0-2]))-(([1-9])|([1][0-9])|([2][0-3])))|(((([0-9])|([1][0-9])|([2][0-2]))-(([1-9])|([1][0-9])|([2][0-3])))\/(([1-9])|([1][0-9])|([2][0-3]))))$/;
       flag=pattern.test(s_item);
       if(!flag){
         return false;
       }
      }
      return true;       
     }
    //validate dayOfMonth '*,/- ? L W C'
    else if(atype==3)
     {
      for(var i=0;i<s_Array.length;i++){
       var s_item = s_Array[i];
       pattern=/^((\?)|(\*)|\*\/(([1-9])|([1][0-9])|([2][0-9])|([3][0-1]))|(([1-9])|([1][0-9])|([2][0-9])|([3][0-1]))|((([1-9])|([1][0-9])|([2][0-9])|([3][0-1]))\/(([1-9])|([1][0-9])|([2][0-9])|([3][0-1])))|((([1-9])|([1][0-9])|([2][0-9])|([3][0]))-(([2-9])|([1][0-9])|([2][0-9])|([3][0-1])))|((([1-9])|([1][0-9])|([2][0-9])|([3][0]))-(([2-9])|([1][0-9])|([2][0-9])|([3][0-1]))\/(([1-9])|([1][0-9])|([2][0-9])|([3][0-1])))|(L)|(((\*)|([1-9])|([1][0-9])|([2][0-9])|([3][0-1]))W)|((([1-9])|([1][0-9])|([2][0-9])|([3][0-1])|(\*))C))$/;
       flag=pattern.test(s_item);
       if(!flag){
         return false;
       }
      }
      return true;       
     }
    else if(atype==4)
     {
      for(var i=0;i<s_Array.length;i++){
       var s_item = s_Array[i];
       pattern=/^((\*)|(\*\/(([1-9])|([1][0-2])))|(([1-9])|([1][0-2]))|((([1-9])|([1][0-2]))\/(([1-9])|([1][0-2])))|((([1-9])|([1][0-1]))-(([2-9])|([1][0-2])))|(((([1-9])|([1][0-1]))-(([2-9])|([1][0-2])))\/(([1-9])|([1][0-2]))))$/;
       flag=pattern.test(s_item);
       if(!flag){
         return false;
       }
      }
      return true;       
     }
    //validate week '*,/- ? L # C'
    else if(atype==5)
     {
      for(var i=0;i<s_Array.length;i++){
       var s_item = s_Array[i];
       pattern=/^((\?)|(\*)|(\*\/([1-7]))|(([1-7]))|(([1-7])\/([1-6]))|(([1-6])-([2-7]))|((([1-6])-([2-7]))\/([1-7]))|(L)|(([1-7])L)|(([1-7])#([1-5]))|(([1-7])C))$/;
       flag=pattern.test(s_item);
       if(!flag){
         return false;
       }
      }
      return true;       
     }
    //validate year '*,/-'     
    else if(atype==6)
     {
      for(var i=0;i<s_Array.length;i++){
       var s_item = s_Array[i];
       pattern=/^((\*)|(\*\/(([1-9])|([1-9][0-9]*)))|(([1-9])|([1-9][0-9]*))|((([1-9])|([1-9][0-9]*))\/(([1-9])|([1-9][0-9]*)))|((([1-9])|([1-9][0-9]*))-(([1-9])|([1-9][0-9]*)))|(((([1-9])|([1-9][0-9]*))-(([1-9])|([1-9][0-9]*)))\/(([1-9])|([1-9][0-9]*))))$/;
       flag=pattern.test(s_item);
       if(!flag){
         return false;
       }
      }
      return true;       
     }
    else{
       return false;
    }
 }
//
String.prototype.trim = function()
{
 return this.replace(/(^\s*)|(\s*$)/g, "");
}