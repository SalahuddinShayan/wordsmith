function lightSwitch() {
   var element = document.body;
   element.classList.toggle("light-mode");
   let labelElement = document.getElementById("DMS");
   var A = labelElement.innerHTML.localeCompare("Light Mode");
   if(A == 0){
   labelElement.innerHTML = "Dark Mode";
   }
   else labelElement.innerHTML = "Light Mode";
}

function EditSwitch() {
	var button = document.getElementById("UES");
	var id = document.getElementById("Id");
	var label = document.getElementById("IdLable");

	   if (id.disabled) {
	      id.disabled = false;
		  id.style.display = "block";
		  button.innerHTML = "Edit"
		  label.style.display = "block";
		  
	    } else {
			id.disabled = true;
		    id.style.display = "none";
   		    button.innerHTML = "Uplopad"
		    label.style.display = "none";
	     }
}