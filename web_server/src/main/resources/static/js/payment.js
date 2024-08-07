


function showPaymentPage() {
    showPage('payment');

    listFlightDetails();
    listPassengerDetails();
    showTotalPrice();

    const selectedCabins = document.querySelectorAll('.cabin.selected');

    selectedCabins.forEach(cabin => {
        console.log(cabin)
    });
}





async function create_reservation() {
    try {
        const username = sessionStorage.getItem('username');
        const password = sessionStorage.getItem('password');

        const response = await fetch(config.reservation_app.url + '/api/reservation/create_reservation', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }

        const data = await response.text(); // or response.json() if your endpoint returns JSON
        const current_pnr = data.toString();
        console.log(current_pnr);

        return current_pnr;
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        return null; // or handle the error as needed
    }
}


function listFlightDetails() {

    const tripType = document.getElementById('tripType');

    const flight_details_container = document.getElementById('flight-details-container');

    const departureFlightDiv = createFlightDiv(selectedDepartureFlight, 'departureFlightDetails');

    flight_details_container.innerHTML = '<h2> Departure Flight Details </h2>';
    flight_details_container.appendChild(departureFlightDiv);

    if (tripType.value === "roundTrip") {
        const returnFlightDiv = createFlightDiv(selectedReturnFlight, 'returnFlightDetails');

        flight_details_container.innerHTML += '<h2> Return Flight Details </h2>';
        flight_details_container.appendChild(returnFlightDiv);
    }
}

function createFlightDiv(flight, divId) {
    const flightDiv = document.createElement('div');
    flightDiv.id = divId;
    flightDiv.className = 'flight-card';

    const flightDetails = `
        <p>Flight ID: ${flight.f_id}</p>
        <p>Date: ${flight.date}</p>
        <p>Time: ${flight.time}</p>
        <p>Arrival Port: ${flight.arrivalPort.portName}</p>
        <p>Departure Port: ${flight.departurePort.portName}</p>
    `;

    flightDiv.innerHTML = flightDetails;

    return flightDiv;
}
    
function listPassengerDetails() {   
    const passenger_details_container = document.getElementById('passenger-details-container');
    passenger_details_container.style.display = 'grid';
    passenger_details_container.style.gridTemplateColumns = 'repeat(3, 1fr)';
    passenger_details_container.style.gridGap = '10px';

    const passengerTypes = passengersData.reduce((types, passenger) => {
        if (!types[passenger.pType]) {
            types[passenger.pType] = [];
        }
        types[passenger.pType].push(passenger);
        return types;
    }, {});

    Object.entries(passengerTypes).forEach(([type, passengers]) => {
        const typeDiv = document.createElement('div');
        typeDiv.className = 'passenger-type';
        typeDiv.innerHTML = `<h2>${type}</h2>`;
        typeDiv.style.display = 'layout';
        passenger_details_container.appendChild(typeDiv);


        passengers.forEach(passenger => {
            const passengerDiv = createPassengerDiv(passenger);
            typeDiv.appendChild(passengerDiv);
        });
    });

}

function createPassengerDiv(passenger) {  
    const passengerDiv = document.createElement('div');
    passengerDiv.className = 'passenger-card';
    
    const passengerDetails = `
        <p>Name: ${passenger.name}</p>
        <p>Surname: ${passenger.surname}</p>
        <p>Age: ${passenger.birthDate}</p>
        <p>Gender: ${passenger.gender}</p>
        <p>Nationality: ${passenger.nationalityNo}</p>
    `;
    
    passengerDiv.innerHTML = passengerDetails;
    
    return passengerDiv;
}

function showTotalPrice() {


    const totalPrice = document.getElementById('totalPrice').textContent;
    // Create a new div element
    const totalPriceDiv = document.createElement('div');
    totalPriceDiv.id = 'totalPriceDiv';
    
    // Create a span to display the total price
    const priceSpan = document.createElement('span');
    priceSpan.id = 'totalPrice';
    priceSpan.textContent = `Total Price: $${totalPrice}`;
    
    // Create the "Make Payment" button
    const paymentButton = document.createElement('button');
    paymentButton.id = 'makePaymentButton';
    paymentButton.textContent = 'Make Payment';
    paymentButton.onclick = function() {
        // Redirect to payment page
        ticketReservation();
        showReservationInfobyPNR(PNR_code);
    }

    
    // Append the price span and button to the div
    totalPriceDiv.appendChild(priceSpan);
    totalPriceDiv.appendChild(paymentButton);
    
    totalPriceDiv.style.display = 'grid';


    // Append the totalPriceDiv to the parent container
    const parentContainer = document.getElementById('payment');
    parentContainer.appendChild(totalPriceDiv);
}


function ticketReservation() {
  const url =config.reservation_app.url + `/api/reservation/ticket_reservation?pnr_code=${PNR_code}`;

  fetch(url, {
    method: 'POST'
  })
  .catch(handleError);
}

function handleResponse(response) {
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  return response.json();
}

function handleError(error) {
  console.error('Error:', error);
}

    

