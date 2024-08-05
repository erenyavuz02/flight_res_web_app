// Assuming the endpoint '/api/ports' returns JSON array of ports
// Fetch the list of ports from the server
document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/ports/get_ports')
        .then(response => response.json())
        .then(data => {
            const departurePortSelect = document.getElementById('departurePort');
            const arrivalPortSelect = document.getElementById('arrivalPort');

            data.forEach(port => {
                console.log(port); // Debugging statement
                const option = document.createElement('option');
                option.value = port.code;
                option.textContent = `${port.portName} (${port.code})`;
                departurePortSelect.appendChild(option);

                const optionClone = option.cloneNode(true);
                arrivalPortSelect.appendChild(optionClone);
            });
        })
        .catch(error => {
            console.error('Error fetching ports:', error);
        });
});


//TODO: change the name of it
document.getElementById('nextButton').disabled = true; // Initially disable the Next button



function searchFlights(event) {

    const inputMessage = checkSearchInputs();

    if (inputMessage !== '') {
        showCustomAlert(inputMessage);
        return;
    }

    event.preventDefault();
    
    var form = document.getElementById('flightReservationForm');
    
    var departurePort = form.departurePort.value;
    var arrivalPort = form.arrivalPort.value;
    var departureDate = form.departureDate.value;
    var returnDate = form.returnDate.value;
    var adults = form.adults.value;
    var children = form.children.value;
    var infants = form.infants.value;

   

    passengers_count.push({ type: 'Adult', count: adults });
    passengers_count.push({ type: 'Child', count: children });
    passengers_count.push({ type: 'Infant', count: infants });    

    console.log('Trip Type:', tripType.value);
    console.log(departurePort, arrivalPort, departureDate, adults, children, infants);
    
    fetch('/api/flights/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            departurePort: departurePort,
            arrivalPort: arrivalPort,
            departureDate: departureDate,
            adults: adults,
            children: children,
            infants: infants
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        displayFlightResults(data, 'departureFlightResults', false);
        if (tripType.value === 'roundTrip') {
            // Fetch return flights if round-trip is selected
            console.log('Round-trip selected, fetching return flights...');
            fetch('/api/flights/search', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    departurePort: arrivalPort,
                    arrivalPort: departurePort,
                    departureDate: returnDate, // Assuming same date for simplicity, adjust as needed
                    adults: adults,
                    children: children,
                    infants: infants
                })
            })
            .then(response => response.json())
            .then(returnData => {
                console.log(returnData);
                //calling function in the show_flights.js
                displayFlightResults(returnData, 'returnFlightResults', true);
            })
            .catch(error => console.error('Error:', error));
        }
        showPage('flight_results');
    })
    .catch(error => console.error('Error:', error));
}


/**
 * Checks the search inputs and returns an error message if any of the inputs are invalid.
 * If all inputs are valid, an empty string is returned.
 *
 * @return {String} Returns an error message if any of the inputs are invalid, otherwise an empty string.
 */
function checkSearchInputs() {
    // Initialize an empty error message
    var message = '';

    // Check if the number of passengers is valid
    if (checkPassengerCount() !== 'valid') {
        // If the number of passengers is invalid, set the error message to the error message returned by checkPassengerCount()
        message = checkPassengerCount();
    } else if (checkPorts() !== 'valid') {
        // If the departure and arrival ports are invalid, set the error message to the error message returned by checkPorts()
        message = checkPorts();
    } else if (checkDates() !== 'valid') {
        // If the departure and arrival dates are invalid, set the error message to the error message returned by checkDates()
        message = checkDates();
    }

    // Return the error message
    return message;
}

/**
 * Checks if the number of passengers is valid.
 * Returns 'valid' if the number of passengers is greater than 0, or an error message otherwise.
 *
 * @return {String} Returns 'valid' or the error message otherwise
 */
function checkPassengerCount(){ 
    // Get the values of the number of passengers
    const adults = document.getElementById('adults').value;
    const children = document.getElementById('children').value;
    const infants = document.getElementById('infants').value;

    // Calculate the total number of passengers
    const totalPassengers = parseInt(adults) + parseInt(children) + parseInt(infants);
    
    // Check if the number of passengers is valid
    if (totalPassengers <= 0){
        // If the number of passengers is not valid, return an error message
        return 'Please enter a valid number of passengers';
    }

    // If the number of passengers is valid, return 'valid'
    return 'valid';
}


/**
 * Checks if the departure and arrival ports are valid.
 * Returns 'valid' if they are different, or an error message if they are the same.
 *
 * @return {String} Returns 'valid' or the error message otherwise
 */
function checkPorts(){
    // Get the values of the departure and arrival ports
    const departurePort = document.getElementById('departurePort').value;
    const arrivalPort = document.getElementById('arrivalPort').value;

    // Check if the departure and arrival ports are the same
    if (departurePort === arrivalPort) {
        // If they are the same, return an error message
        return 'Departure and Arrival ports cannot be the same';
    } else {
        // If they are different, return 'valid'
        return 'valid';
    }

}

/**
 * Checks if the departure date is valid and if the return date is valid
 * for a round trip.
 *
 * @return {String} Returns 'valid' or the error message otherwise
 */
function checkDates() {
    const departureDate = new Date(document.getElementById('departureDate').value);
    const returnDate = new Date(document.getElementById('returnDate').value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    if (departureDate >= today) {
        if (document.getElementById('tripType').value === 'roundTrip') {
            if (returnDate >= departureDate && returnDate >= today) {
                return 'valid';
            } else {
                return 'Return date must be after departure date and today';
            }

            
        }
        return 'valid';
    } else {
        return 'Departure date must be after today';
    }
}


