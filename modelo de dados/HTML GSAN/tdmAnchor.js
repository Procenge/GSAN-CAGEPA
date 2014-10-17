function getUrlForFrame()
{
var myItem, myPosition, newUrl, myExtension, myObjectLink;
myExtension = window.location.href.split(".").pop(); 
myFileExtension = myExtension.substr(0, myExtension.indexOf("#"));
myPosition = window.location.href.lastIndexOf(".");
myItem = window.location.href.substr(0, myPosition);
myObjectLink = window.location.href.substr(window.location.href.indexOf("#"));
if (window.location.href.lastIndexOf("#") != -1)
  {	
	  newUrl = myItem+"B."+myFileExtension+myObjectLink;
	  window.frames['body'].location.href = newUrl;
  }
} 

