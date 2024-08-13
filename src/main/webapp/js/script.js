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