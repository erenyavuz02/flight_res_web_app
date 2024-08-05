



function displayFlightResults(flights, containerId, isReturn) {
    
    var resultsDiv = document.getElementById(containerId);
    resultsDiv.innerHTML = '';
    
    flights.forEach(flight => {
        console.log(flight);
        var flightDiv = document.createElement('div');
        flightDiv.className = 'flight-card';
        flightDiv.setAttribute('data-id', flight.f_id);
        flightDiv.setAttribute('flight', flight);
        //flightDiv.onclick = function() { selectFlight(flight.f_id, containerId); };
        
        var flightDetails = `
            <p>Flight ID: ${flight.f_id}</p>
            <p>Date: ${flight.date}</p>
            <p>Time: ${flight.time}</p>
            <p>Arrival Port: ${flight.arrivalPort.portName}</p>
            <p>Departure Port: ${flight.departurePort.portName}</p>
        `;
        
        flightDiv.innerHTML = flightDetails;

        // Fetch flight details for each cabin
        fetch(`/api/flightDetails/?flightId=${flight.f_id}`)
        .then(response => response.json())
        .then(details => {
            console.log(details);
            details.forEach(detail => {
                var cabinDiv = document.createElement('div');
                cabinDiv.className = 'cabin';
                cabinDiv.setAttribute('data-id', `${flight.f_id}-${detail.cabin}`);
                cabinDiv.setAttribute('flight-id', flight.f_id);
                cabinDiv.setAttribute('cabin', detail.cabin);
                cabinDiv.setAttribute('data-price', detail.price);
                cabinDiv.onclick = function() { 
                    selectFlight(flight, detail, containerId, isReturn);
                 };
                
                var cabinInfo = `
                    <h3>${detail.cabin}</h3>
                    <p>Price: ${detail.price}</p>
                    <p>Available Seats: ${detail.availableSeats}</p>
                `;
                
                cabinDiv.innerHTML = cabinInfo;
                flightDiv.appendChild(cabinDiv);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
        resultsDiv.appendChild(flightDiv);
    });
}

var selectedDepartureFlight = null;
var selectedDepartureCabin = null;

var selectedReturnFlight = null;
var selectedReturnCabin = null;

function selectFlight(flight, cabin, containerId, isReturn) {
    const flightId = flight.f_id

    if (isReturn){
        selectedReturnFlight = flight;
        selectedReturnCabin = cabin;
    }
    else{
        selectedDepartureFlight = flight;
        selectedDepartureCabin = cabin;
    }
    console.log(selectedDepartureCabin);
    console.log(selectedDepartureFlight);
    console.log(selectedReturnFlight);
    // Clear all selections in the current container
    document.querySelectorAll(`#${containerId} .flight-card`).forEach(card => {
        card.classList.remove('selected');
    });
    document.querySelectorAll(`#${containerId} .flight-card .cabin`).forEach(card => {
        card.classList.remove('selected');
    });
    
    // Select the clicked cabin
    var selectedCabin = document.querySelector(`#${containerId} .cabin[data-id="${flightId}-${cabin.cabin}"]`);
    
    if (selectedCabin) {
        selectedCabin.classList.add('selected');
        selectedCabin.closest('.flight-card').classList.add('selected');
        selectedCabin.setAttribute('flight-id', flightId);
    } else {
        console.error(`No cabin found with id ${flightId}-${cabin.cabin} in ${containerId}`);
        return;
    }

    // Calculate total price (ensure this function exists)
    calculateTotalPrice();
  
}



function calculateTotalPrice() {
    console.log("I am here");
    let totalPrice = 0;

    var totalPassengers = 0;
    

    passengers_count.forEach(passenger => {
        totalPassengers += parseInt(passenger.count);
        console.log(passenger.type, passenger.count);
    })
    // Select all the selected cabin elements
    const selectedCabins = document.querySelectorAll('.cabin.selected');

    selectedCabins.forEach(cabin => {
        const priceText = cabin.getAttribute('data-price');
        const price = parseFloat(priceText.replace('Price: ', ''));
        totalPrice += totalPassengers * price;
    });

    // Update the total price in the HTML
    document.getElementById('totalPrice').innerText = totalPrice.toFixed(2);
}


/**
 * Checks if the selected flights have enough seats for the passengers.
 * Returns 'valid' if the selection is valid, or an error message otherwise.
 *
 * @return {String} Returns 'valid' or the error message otherwise
 */
function checkSelectedFlights() {
    // Get the trip type
    const tripType = document.getElementById('tripType').value;

    // Get the number of passengers
    const adults = parseInt(document.getElementById('adults').value);
    const children = parseInt(document.getElementById('children').value);
    const infants = parseInt(document.getElementById('infants').value);
    const totalPassengers = adults + children + infants;

 
    // Check if a departure flight is selected
    if (selectedDepartureFlight === null) {
        return 'Please select a departure flight';
    }

       // Check if the selected departure flight has enough seats
    if (selectedDepartureCabin.availableSeats < totalPassengers){
        return 'Not enough seats available for the selected departure flight';
    }


    console.log("I am here now");
    // Check if a round-trip selection is selected
    if (tripType === 'roundTrip') {
        console.log("It is a round trip");
        // Check if both departure and return flights are selected
        if ( selectedReturnFlight === null) {
            console.log("Please select both departure and return flights");
            return 'Please select both departure and return flights';
            
        }

        // Check if the selected return flight has enough seats
        if (selectedReturnCabin.availableSeats < totalPassengers) {
            return 'Not enough seats available for the selected return flight';
        }

        const departureTime = new Date(`${selectedDepartureFlight.date}T${selectedDepartureFlight.time}`);
        const returnTime = new Date(`${selectedReturnFlight.date}T${selectedReturnFlight.time}`);

        if (returnTime < departureTime) {
            return 'Return flight cannot be before departure flight';
        }
    }


    
    return 'valid';
}

function goToPassengerInfo() {
    console.log("going to passsenger info");
    const message = checkSelectedFlights();
    if (message !== 'valid') {
        showCustomAlert(message);
        return;
    }
    showPage('passenger_info');
    createPassengerForms();
}