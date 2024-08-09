

var PNR_code = null;
function search_by_PNR(){

    PNR_code = document.getElementById("pnr_code_search").value;

    if(PNR_code == ""){
        showCustomAlert("Please enter PNR code");
        return;
    }

    showReservationInfobyPNR(PNR_code);
}



function showReservationInfobyPNR(PNR_code) {
    
    fetchFlightsByPnrCode(PNR_code)
        .then(flights => {
            if (flights.length === 0) {
                showCustomAlert("No flights reserved for the given PNR code");
                return;
            }
            
            displayFlights(flights);
            fetchPassengersByPnrCode(PNR_code);
            
            showPage('reservationInfo');
        })
        .catch(error => {
            showCustomAlert("Invalid PNR code");
        });
}


//this function gets flights and details of a reservation by using its PNR code
async function fetchFlightsByPnrCode(pnrCode) {
    const response = await fetch(config.reservation_app.url + `/api/reservedFlight/getFlightsByPnrCode?PNR_code=${encodeURIComponent(pnrCode)}`, {
        method: 'GET',
    });
    if (!response.ok) {
        throw new Error('Network response was not ok.');
    }
    
    const flights = await response.json();
    console.log('Fetched flights:', flights);
    return flights;
}

function displayFlights(flights) {
    const resultsDiv = document.getElementById('reservationInfo-FlightsContainer');
    resultsDiv.innerHTML = ''; // Clear previous results

    //add pnr code as heading at the top
    const pnrCodeDiv = document.createElement('div');
    pnrCodeDiv.className = 'pnr-code';
    pnrCodeDiv.textContent = `PNR code: ${PNR_code}`;
    resultsDiv.appendChild(pnrCodeDiv);

    flights.forEach(flight => {
        const flightDiv = document.createElement('div');
        flightDiv.className = 'flight-card';
        flightDiv.setAttribute('data-id', flight.f_id);

        const flightDetails = `
            <p>Flight ID: ${flight.f_id}</p>
            <p>Date: ${flight.date}</p>
            <p>Time: ${flight.time}</p>
            <p>Arrival Port: ${flight.arrivalPort.portName}</p>
            <p>Departure Port: ${flight.departurePort.portName}</p>
        `;

        flightDiv.innerHTML = flightDetails;
        resultsDiv.appendChild(flightDiv);
    });
}

//this function gets flights and details of a reservation by using its PNR code
function fetchPassengersByPnrCode(pnrCode) {
    fetch(config.reservation_app.url + `/api/passenger/searchByPNR?pnr_code=${encodeURIComponent(pnrCode)}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(passengers => {
            console.log('Fetched Passengers:', passengers);
            // Process the flights data here
            displayPassengers(passengers); // Assuming you have a function to display flights
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

//this function gets passenger info by using its PNR code
function displayPassengers(passengers) {
    const resultsDiv = document.getElementById('reservationInfo-PassengersContainer');
    resultsDiv.innerHTML = ' '; // Clear previous results

    const passengers_count = passengers.length;

    passengers.forEach(passenger => {

        const list = document.createElement('div');
        list.id = 'passenger-list';
        list.className = 'passenger-list';

        const passengerDiv = document.createElement('div');
        passengerDiv.className = 'passenger-card';

        const passengerDetails = `
            <p>Name: ${passenger.name}</p>
            <p>Surname: ${passenger.surname}</p>
            <p>Age: ${passenger.birthDate}</p>
            <p>Gender: ${passenger.gender}</p>
            <p>Nationality No: ${passenger.nationalityNo}</p>
        `;

        passengerDiv.innerHTML = passengerDetails;
        list.appendChild(passengerDiv);

        if (passengers_count > 1) {
            cancelPassengerButton = document.createElement('button');
            cancelPassengerButton.textContent = 'Cancel Passenger';
            cancelPassengerButton.addEventListener('click', function() {
                cancelPassenger(passenger);
            })
            list.appendChild(cancelPassengerButton);
        }

        resultsDiv.appendChild(list);
    });



    const deleteResButton = document.createElement('button');
    deleteResButton.textContent = 'Delete Reservation';
    deleteResButton.addEventListener('click', function() {
        deleteReservation();
    });
    resultsDiv.appendChild(deleteResButton);
}


function deleteReservation() {



    fetch(config.reservation_app.url + `/api/reservation/delete_reservation?pnr_code=${encodeURIComponent(PNR_code)}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                console.log('Reservation deleted successfully.');
                window.location.href = 'main_page.html';
                return response.json();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
}

function cancelPassenger(passenger) {


    fetch(config.reservation_app.url + `/api/passenger/delete?pnr_code=${encodeURIComponent(PNR_code)}&passportNo=${encodeURIComponent(passenger.passportNo)}`, {


        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                console.log('Passenger deleted successfully.');
                showReservationInfobyPNR(PNR_code);
                return response.json();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
}