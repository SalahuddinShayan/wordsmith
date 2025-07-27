function lightSwitch() {
   var element = document.getElementById("CS");
   element.classList.toggle("light-mode");
   if (localStorage.getItem("mode") === null) {
	localStorage.setItem("mode", "light");
	}
	else if (localStorage.getItem("mode") === "dark") {
		localStorage.setItem("mode", "light");
		}
	else{
		localStorage.setItem("mode", "dark");
		}	
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
	var el = document.getElementById('CS');
	var style = window.getComputedStyle(el, null).getPropertyValue('font-size');
	var fontSize = parseFloat(style); 
	el.style.fontSize = (fontSize + 1) + 'px';
	localStorage.setItem("fs", fontSize + 1);
}

function TextSizeDefault(){
	var el = document.getElementById('CS');
	el.style.fontSize = 16 + 'px';
	localStorage.setItem("fs", 16);
}

function TextSizeDecrease(){
	var el = document.getElementById('CS');
	var style = window.getComputedStyle(el, null).getPropertyValue('font-size');
	var fontSize = parseFloat(style); 
	el.style.fontSize = (fontSize - 1) + 'px';
	localStorage.setItem("fs", fontSize - 1);
}

function ChapStyle(){
	if (localStorage.getItem("fs") != null) {
		var el = document.getElementById('CS');
		el.style.fontSize = (localStorage.getItem("fs") + 'px');
	}
	
	if (localStorage.getItem("mode") != null) {
			if (localStorage.getItem("mode") === "light") {
				var element = document.getElementById("CS");
				  element.classList.toggle("light-mode");
			}
		}
}


            function toggleReplyForm(commentId) {
                var form = document.getElementById('reply-form-' + commentId);
                if (form.style.display === 'none' || form.style.display === '') {
					console.log('if block"');
                    form.style.display = 'block';
                } else {
					console.log('else block"');
                    form.style.display = 'none';
                }
            }
            