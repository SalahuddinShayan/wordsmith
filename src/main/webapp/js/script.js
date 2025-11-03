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

			
function validateImageSize() {
    const fileInput = document.getElementById('image');
    const file = fileInput.files[0];
    const maxSize = 1024 * 1024; // 1MB limit

    if (file && file.size > maxSize) {
        alert("File size must be less than 1MB.");
        return false; // Prevent form submission
    }
    return true;
}


document.addEventListener("DOMContentLoaded", function() {
    const csrfToken = document.getElementById("_csrf").value;

    document.querySelectorAll(".like-btn, .dislike-btn, .reaction-btn")
        .forEach(button => {
            button.addEventListener("click", function() {
                const entityType = this.dataset.entityType;
                const entityId = this.dataset.entityId;
                let likeStatus = this.dataset.likeStatus;

                // Toggle off if already active
                if (this.classList.contains("active")) {
                    likeStatus = "NONE";
                }

                fetch("/save-like", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "X-CSRF-TOKEN": csrfToken
                    },
                    body: `entityType=${entityType}&entityId=${entityId}&likeStatus=${likeStatus}`
                })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        // CASE 1: Comment (👍 / 👎)
                        if (entityType === "COMMENT") {
                            const likeBtn = document.querySelector(`#like-section-${entityId} .like-btn`);
                            const dislikeBtn = document.querySelector(`#like-section-${entityId} .dislike-btn`);

                            document.getElementById(`like-count-${entityId}`).innerText = data.likes;
                            document.getElementById(`dislike-count-${entityId}`).innerText = data.dislikes;

                            likeBtn.classList.remove("active");
                            dislikeBtn.classList.remove("active");

                            if (likeStatus === "LIKE") {
                                likeBtn.classList.add("active");
                            } else if (likeStatus === "DISLIKE") {
                                dislikeBtn.classList.add("active");
                            }
                        }

                        // CASE 2: Novel (Bookmark)
                        else if (entityType === "NOVEL") {
                            const bookmarkBtn = document.querySelector(`#novel-like-section-${entityId} .bookmark-btn`);
                            document.getElementById(`bookmark-count-${entityId}`).innerText = data.likes;

                            bookmarkBtn.classList.remove("active");
                            if (likeStatus === "LIKE") {
                                bookmarkBtn.classList.add("active");
                            }
                        }

                        // CASE 3: Chapter (6 reactions)
                        else if (entityType === "CHAPTER") {
                            // Reset all reaction buttons
                            const section = document.querySelector(`#reaction-section-${entityId}`);
                            section.querySelectorAll(".reaction-btn").forEach(btn => btn.classList.remove("active"));

                            // Update counts for each reaction type
                            for (const [reaction, count] of Object.entries(data.reactions)) {
                                document.getElementById(`reaction-count-${reaction.toUpperCase()}-${entityId}`).innerText = count;
                            }

                            // Reactivate selected button
                            if (likeStatus !== "NONE") {
                                const activeBtn = section.querySelector(`[data-like-status="${likeStatus}"]`);
                                if (activeBtn) activeBtn.classList.add("active");
                            }
                        }
                    } else {
                        alert(data.message);
                    }
                })
                .catch(err => console.error("Error saving like:", err));
            });
        });


        document.querySelectorAll(".favorite-btn").forEach(button => {
        button.addEventListener("click", function() {
            const novelId = this.dataset.novelId;

            fetch("/toggle-favorite", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-CSRF-TOKEN": csrfToken
                },
                body: `novelId=${novelId}`
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    document.getElementById(`favorite-count-${novelId}`).innerText = data.count;

                    if (data.favorited) {
                        this.classList.add("active");
                    } else {
                        this.classList.remove("active");
                    }
                } else {
                    alert(data.message);
                }
            })
            .catch(err => console.error("Error toggling favorite:", err));
        });
    });
});



// Remove sidebar ads on mobile devices
document.addEventListener("DOMContentLoaded", function () {
  function handleSidebarAds() {
    const leftAd = document.getElementById("leftSidebarAd");
    const rightAd = document.getElementById("rightSidebarAd");
    const leftAd2 = document.getElementById("leftSidebarAd2");
    const rightAd2 = document.getElementById("rightSidebarAd2");

    if (window.innerWidth < 768) {
      // Mobile breakpoint (Bootstrap 'md')
      if (leftAd) leftAd.remove();
      if (rightAd) rightAd.remove();
      if (leftAd2) leftAd2.remove();
      if (rightAd2) rightAd2.remove();
    }
  }

  // Run once on load
  handleSidebarAds();

  // Optional: recheck if screen resizes (e.g., device rotation)
  window.addEventListener("resize", function () {
    handleSidebarAds();
  });
});


document.addEventListener("DOMContentLoaded", () => {
    const addFormSection = document.getElementById("add-form-section");
    const editFormSection = document.getElementById("edit-form-section");
    const editForm = document.getElementById("edit-form");

    // Handle Edit button click
    document.querySelectorAll(".edit-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            const title = btn.dataset.title;
            const content = btn.dataset.content;

            document.getElementById("edit-id").value = id;
            document.getElementById("edit-title").value = title;
            document.getElementById("edit-content").value = content;

            editForm.action = `/announcements/edit`;

            addFormSection.style.display = "none";
            editFormSection.style.display = "block";
            window.scrollTo({ top: 0, behavior: "smooth" });
        });
    });

    // Handle cancel button
    document.getElementById("cancel-edit").addEventListener("click", () => {
        editFormSection.style.display = "none";
        addFormSection.style.display = "block";
        editForm.reset();
    });
});
