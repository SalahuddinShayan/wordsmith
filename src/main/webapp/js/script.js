function lightSwitch() {
   var element = document.getElementById("CS");
   element.classList.toggle("light-mode");
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

function TextSizeIncrease(){
	var el = document.getElementById('foo');
	var style = window.getComputedStyle(el, null).getPropertyValue('font-size');
	var fontSize = parseFloat(style); 
	// now you have a proper float for the font size (yes, it can be a float, not just an integer)
	el.style.fontSize = (fontSize + 1) + 'px';
}