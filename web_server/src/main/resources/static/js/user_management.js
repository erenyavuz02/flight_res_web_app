
function addUser() {
  const form = document.getElementById('addUserForm');
  const formData = new FormData(form);

  if (formData.get('password_add') !== formData.get('password_add_again')) {
    showCustomAlert('Passwords do not match');
    return;
  }

  const user = {
    username: formData.get('username_add'),
    password: formData.get('password_add'),
    email: formData.get('email_add'),
  };

  console.log(user);

  fetch('/create_user', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(user),
  })
    .then((response) => {
      if (!response.ok) {
        showCustomAlert(`New user could not be added: ${response.statusText}`);
      }
      showCustomAlert('User added');
    })
    .catch((error) => {
      showCustomAlert(`New user could not be added: ${error.message}`);
    });
}


function deleteUser() {
  const username = document.getElementById('deleteUsername').value;
  const url = `/delete?userName=${username}`;

  fetch(url, {
    method: 'DELETE',
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Failed to delete user');
      }
      return response.json();
    })
    .then((data) => {
      showCustomAlert(data.message);
    })
    .catch((error) => {
      showCustomAlert(`Failed to delete user: ${error.message}`);
    });
}
