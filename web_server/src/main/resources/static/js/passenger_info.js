

function createPassengerForms() {
    const passengerFormsContainer = document.getElementById('passenger-form-container');

    //empty passenger form container
    passengerFormsContainer.innerHTML = '';

    passengers_count.forEach((passengerType, index) => {
        for (let i = 1; i <= passengerType.count; i++) {
            const formGroup = document.createElement('div');
            formGroup.setAttribute('p_type', passengerType.type);
            formGroup.classList.add('passenger-form-group');

            // Create a paragraph element for instructions above the form inputs
            const instruction = document.createElement('p');
            instruction.textContent = `Enter ${passengerType.type} ${i} information:`;
            instruction.classList.add('passenger-form-instruction');
            formGroup.appendChild(instruction);

            // Passport Number
            formGroup.appendChild(createFieldWithLabel(`Passport Number:`, `passenger_${passengerType.type.toLowerCase()}_${i}.passportNo`, 'text', 'Passport Number'));

            // Name
            formGroup.appendChild(createFieldWithLabel(`Name:`, `passenger_${passengerType.type.toLowerCase()}_${i}.name`, 'text', 'Name'));

            // Surname
            formGroup.appendChild(createFieldWithLabel(`Surname:`, `passenger_${passengerType.type.toLowerCase()}_${i}.surname`, 'text', 'Surname'));

            // Nationality Number
            formGroup.appendChild(createFieldWithLabel(`Nationality Number:`, `passenger_${passengerType.type.toLowerCase()}_${i}.nationalityNo`, 'text', 'Nationality Number'));

            // Birth Date
            formGroup.appendChild(createFieldWithLabel(`Birth Date:`, `passenger_${passengerType.type.toLowerCase()}_${i}.birthDate`, 'date', 'Birth Date'));

            // Telephone Number
            formGroup.appendChild(createFieldWithLabel(`Telephone Number:`, `passenger_${passengerType.type.toLowerCase()}_${i}.telNo`, 'text', 'Telephone Number'));

            // Gender
            formGroup.appendChild(createGenderChooser());

            // Email
            formGroup.appendChild(createFieldWithLabel(`Email:`, `passenger_${passengerType.type.toLowerCase()}_${i}.email`, 'email', 'Email'));


            passengerFormsContainer.appendChild(formGroup);
        }

        

        
    });
 
}

function createFieldWithLabel(labelText, inputId, inputType, placeholder) {
    const label = document.createElement('label');
    label.textContent = labelText;
    label.classList.add('passenger-form-label');

    const input = document.createElement('input');
    input.id = inputId;
    input.type = inputType;
    input.placeholder = placeholder;
    input.classList.add('passenger-form-input');

    const div = document.createElement('div');
    div.classList.add('passenger-form-field');
    div.appendChild(label);
    div.appendChild(input);

    return div;
}

function createGenderChooser(){
    const label = document.createElement('label');
    label.textContent = "Gender:";
    label.classList.add('passenger-form-label');

    const select = document.createElement('select');
    select.id = "gender";
    select.classList.add('passenger-form-select');
    select.appendChild(createGenderOption("Male"));
    select.appendChild(createGenderOption("Female"));
    select.appendChild(createGenderOption("Other"));
    select.appendChild(createGenderOption("Prefer not to say"));

    const div = document.createElement('div');
    div.classList.add('passenger-form-field');
    div.appendChild(label);
    div.appendChild(select);
    return div;
}

createGenderOption = (value) => {
    const option = document.createElement('option');
    option.id = "gender-option";
    option.value = value.toLowerCase().replace(/\s+/g, '-'); // converts "Prefer not to say" to "prefer-not-to-say"
    option.textContent = value;
    return option;
}

const passengersData = [];
var PNR_code = null;

function collectPassengerInfo() {
    // Reset the passengersData array before collecting new data by removing data from previous form submissions
    passengersData.length = 0;
    var passengersValid = true;

    // Iterate through each form group
    document.querySelectorAll('.passenger-form-group').forEach(formGroup => {
        // Initialize an object to store current passenger's data
        const passengerData = {};

        // Iterate through input fields within the current form group
        formGroup.querySelectorAll('.passenger-form-input').forEach(input => {
            const fieldName = input.id.split('.').slice(-1)[0]; // Extract field name (e.g., passportNo, name, etc.)
            passengerData[fieldName] = input.value; // Store field value in object
        });
        
        passengerData['gender'] = document.getElementById('gender-option').value;
        passengerData['pType'] = formGroup.getAttribute('p_type');
        // Add current passenger's data to array
        passengersData.push(passengerData);

        const message = validatePassengerData(passengerData);
        if (message!= 'valid' ) {
            showCustomAlert(message);
            passengersValid = false;
            return;
        }

    });
    console.log(passengersData);

    if (!passengersValid) {
        return;
    }


    create_reservation().then((current_pnr) => {
        PNR_code = current_pnr;
        console.log(PNR_code);
    }).then(() => {
        reserveFlight(PNR_code, selectedDepartureFlight.f_id, selectedDepartureCabin).then((reservedFlight) => {
            console.log(reservedFlight);
        });

        if (selectedReturnFlight) {
            reserveFlight(PNR_code, selectedReturnFlight.f_id, selectedReturnCabin).then((reservedFlight) => {
                console.log(reservedFlight);
            });
        }
    }).then(() => {
        sendPassengerInfo(PNR_code);
        showPaymentPage();
    });

    


    
}

function validatePassengerData(passenger) {
    const { passportNo, name, surname, nationalityNo, birthDate, telNo, gender, email, pType } = passenger;

    console.log(passenger);
    if (!passportNo || passportNo.length !== 9 || !/^\d{9}$/.test(passportNo)) {
        console.log(passportNo);
        return 'Please enter a valid passport number';
    }

    if (!name || !/^[a-zA-Z\s]+$/.test(name)) {
        return 'Please enter a valid name';
    }

    if (!surname || !/^[a-zA-Z\s]+$/.test(surname)) {
        return 'Please enter a valid surname';
    }

    if (!nationalityNo || nationalityNo.length !== 11 || !/^\d{11}$/.test(nationalityNo)) {
        return 'Please enter a valid nationality number';
    }

    if (!birthDate) {
        return 'Please enter a valid birth date';
    }

    const currentYear = new Date().getFullYear();
    const adultAge = currentYear - 18;
    const childAge = currentYear - 3;
    const maxInfantAge = currentYear - 3;

    const birthYear = new Date(birthDate).getFullYear();

    console.log(birthYear.valueOf())
    console.log(adultAge.valueOf())
    console.log(birthYear.valueOf() < adultAge.valueOf())

    if (pType === 'Adult' && birthYear > adultAge) {
        return 'Adults must be at least 18 years old';
    }

    if (pType === 'Child' && birthYear > childAge) {
        return 'Children must be at least 3 years old';
    }

    if (pType === 'Child' && birthYear <= adultAge) {
        return 'Children must be at most 18 years old';
    }

    if (pType === 'Infant' && birthYear <= childAge) {
        return 'Infants must be at most 3 years old';
    }

    if (!telNo || telNo.length !== 10 || !/^\d{10}$/.test(telNo)) {
        return 'Please enter a valid telephone number';
    }

    if (!email || !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
        return 'Please enter a valid email address';
    }

    return 'valid';
}

async function reserveFlight(PNR, flightId, cabin) {
    const url = '/api/reservedFlight/reserveFlight';
    const params = new URLSearchParams();
    params.append('PNR_code', PNR);
    params.append('flightId', flightId);
    params.append('cabin', cabin.cabin);

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        });

        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }

        const reservedFlight = await response.json();
        console.log('Reserved Flight:', reservedFlight);
        return reservedFlight;
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        return null;
    }
}


function sendPassengerInfo(pnr_code) {
    // Example: Send data to server via fetch POST request
    passengersData.forEach(passenger => {
        passenger['pnr_code'] = pnr_code;
        console.log(passenger);


        fetch('/api/passenger/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(passenger),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Passenger data sent successfully:', data);
    
            // Optionally handle server response
        })
        .catch(error => {
            console.error('Error sending passenger data:', error);
            // Optionally handle error
        });
    })

   
}
