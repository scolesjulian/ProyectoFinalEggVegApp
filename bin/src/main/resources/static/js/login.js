const registro = document.querySelector("#form-registro");

registro.addEventListener("submit", (e) => {
  e.preventDefault();
  const nombre = document.querySelector("#nombre").value;
  const apellido = document.querySelector("#apellido").value;
  const edad = document.querySelector("#edad").value;
  const email = document.querySelector("#email").value;
  const password = document.querySelector("#password").value;

  console.log(nombre);
  console.log(apellido);
  console.log(edad);
  console.log(email);
  console.log(password);

  console.log(e);
});

/* 
 
    */
