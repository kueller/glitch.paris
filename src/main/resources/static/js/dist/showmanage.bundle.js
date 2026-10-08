class Band {
    uuid = null;
    headliner = false;
    performance_date = null;
}


class Venue {
    uuid = null;
}


const BANDS_TO_ADD = {};


const VENUE_TO_ADD = new Venue();


const lockSelectHeight = (select) => {
    if (!select.style.height) {
        select.style.height = select.getBoundingClientRect().height + 'px';
    }
};


const init = () => {
    const bandSearchBtn = document.getElementById("band-search-btn");
    const bandAddNewBtn = document.getElementById("add-band");
    const bandSelectBtn = document.getElementById("select-band");
    const bandUpBtn = document.getElementById("band-up");
    const bandDownBtn = document.getElementById("band-down");
    const bandNewSubmitBtn = document.getElementById("new-band-submit");

    const venueSearchBtn = document.getElementById("venue-search-btn");
    const venueAddNewBtn = document.getElementById("add-venue");
    const venueSelectBtn = document.getElementById("select-venue");
    const venueNewSubmitBtn = document.getElementById("new-venue-submit");

    const showTypeSelect = document.getElementById("type-select");

    const bandOverlay = document.getElementById("new-band");
    const venueOverlay = document.getElementById("new-venue");

    const eventForm = document.getElementById("event-form");

    bandSearchBtn.addEventListener("click", event_BandSearch);
    bandAddNewBtn.addEventListener("click", event_BandAddNewOpen);
    bandSelectBtn.addEventListener("click", event_BandSelect);
    bandUpBtn.addEventListener("click", event_BandUp);
    bandDownBtn.addEventListener("click", event_BandDown);
    bandNewSubmitBtn.addEventListener("click", event_BandAddNewSubmit);

    venueSearchBtn.addEventListener("click", event_VenueSearch);
    venueAddNewBtn.addEventListener("click", event_VenueAddNewOpen);
    venueSelectBtn.addEventListener("click", event_VenueSelect);
    venueNewSubmitBtn.addEventListener("click", event_VenueAddNewSubmit);

    showTypeSelect.addEventListener("change", event_ShowHideFestivalOnly);

    lockSelectHeight(document.getElementById("band-search-result"));
    lockSelectHeight(document.getElementById("selected-bands"));
    lockSelectHeight(document.getElementById("venue-search-result"));

    bandOverlay.addEventListener("click", event_BandAddNewCancel);
    bandOverlay.style.removeProperty("opacity");

    venueOverlay.addEventListener("click", event_VenueAddNewCancel);
    venueOverlay.style.removeProperty("opacity");

    eventForm.addEventListener("submit", event_Submit);
};


const removeChildren = (element) => {
    while (element.firstChild) {
        element.removeChild(element.lastChild);
    }
};


const setPopUpError = (errorContainer, message) => {
    let section = errorContainer.firstChild;

    if (!section) {
        section = document.createElement("p");
        section.style.color = "red";
        errorContainer.appendChild(section);
    }

    section.innerText = message;
};


const selectBand = (uuid, bandName) => {
    const bandSelected = document.getElementById("selected-bands");
    const bandDateSelect = document.getElementById("festival-per-band-date-select");

    const newSelected = document.createElement("option");
    newSelected.value = uuid;
    newSelected.innerText = bandName;

    bandSelected.appendChild(newSelected);

    let performanceId = "band-" + uuid;
    const section = document.createElement("div");
    section.classList.add("form-row");

    const label = document.createElement("label");
    label.for = performanceId;
    label.innerText = bandName;
    section.appendChild(label);

    const dateSelect = document.createElement("input");
    dateSelect.type = "date";
    dateSelect.id = performanceId;
    section.appendChild(dateSelect);

    bandDateSelect.appendChild(section);

    let band = new Band();
    band.uuid = uuid;

    BANDS_TO_ADD[band.uuid] = band;
};


const selectVenue = (uuid, venueName) => {
    const venueSelected = document.getElementById("selected-venue");
    const venueIdSelected = document.getElementById("selected-venue-uuid");

    venueSelected.value = venueName;
    venueIdSelected.value = uuid;

    VENUE_TO_ADD.uuid = uuid;
};


const event_BandSearch = () => {
    const bandSearchQuery = document.getElementById("band-search-query");
    const bandSearchResult = document.getElementById("band-search-result");

    fetch("/api/director/shows/bands/" + bandSearchQuery.value.trim() + "/search",
        {
            method: "GET",
            credentials: "same-origin",
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error: Received code: " + response.status)
            }

            return response.json();
        })
        .then(data => {
            removeChildren(bandSearchResult);

            for (let i in data.bands) {
                const result = document.createElement("option");
                result.value = data.bands[i]["uuid"];
                result.innerText = data.bands[i]["name"];

                bandSearchResult.appendChild(result);
            }
        })
        .catch(error => {
            console.log(error);
        });
};


const event_BandAddNewOpen = () => {
    const overlay = document.getElementById("new-band");
    overlay.classList.add("active");
};


const event_BandAddNewCancel = (event) => {
    const overlay = document.getElementById("new-band");

    if (event.target.id === "new-band" || event.target.id === "new-band-cancel") {
        overlay.classList.remove("active");
    }
};


const event_BandAddNewSubmit = () => {
    const overlay = document.getElementById("new-band");
    const error = document.getElementById("new-band-error");

    const bandName = document.getElementById("new-band-name").value.trim();
    const lastfmUrl = document.getElementById("new-band-lastfmurl").value.trim();

    if (bandName === "") {
        setPopUpError(error, "Name cannot be empty");
        return;
    }

    fetch("/api/director/shows/band",
        {

            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "same-origin",
            body: JSON.stringify(
                {
                    "name": bandName,
                    "lastfm_url": lastfmUrl === "" ? null : lastfmUrl,
                }
            ),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Received code: " + response.status + " " + response.statusText);
            }

            return response.json();
        })
        .then(data => {
            selectBand(data["uuid"], data["name"]);
            overlay.classList.remove("active");
        })
        .catch(responseError => {
            setPopUpError(error, responseError);
        });
};


const event_BandSelect = () => {
    const bandSearchResult = document.getElementById("band-search-result");
    if (bandSearchResult.selectedIndex < 0) return;

    const selected = bandSearchResult.options[bandSearchResult.selectedIndex];

    selectBand(selected.value, selected.innerText);
};


const event_BandUp = () => {
    const bandSelected = document.getElementById("selected-bands");
    if (bandSelected.selectedIndex < 1) return;
    if (bandSelected.selectedOptions.length !== 1) return;

    const selected = bandSelected.options[bandSelected.selectedIndex];
    const prev = bandSelected.options[bandSelected.selectedIndex - 1];

    let uuidTmp = prev.value;
    let nameTmp = prev.innerText;

    prev.value = selected.value;
    prev.innerText = selected.innerText;

    selected.value = uuidTmp;
    selected.innerText = nameTmp;
};


const event_BandDown = () => {
    const bandSelected = document.getElementById("selected-bands");
    if (bandSelected.selectedIndex < 0) return;
    if (bandSelected.selectedOptions.length !== 1) return;
    if (bandSelected.selectedIndex > (bandSelected.options.length - 2)) return;

    const selected = bandSelected.options[bandSelected.selectedIndex];
    const next = bandSelected.options[bandSelected.selectedIndex + 1];

    let uuidTmp = next.value;
    let nameTmp = next.innerText;

    next.value = selected.value;
    next.innerText = selected.innerText;

    selected.value = uuidTmp;
    selected.innerText = nameTmp;
};


const event_VenueSearch = () => {
    const venueSearchQuery = document.getElementById("venue-search-query");
    const venueSearchResult = document.getElementById("venue-search-result");

    fetch("/api/director/shows/venues/" + venueSearchQuery.value.trim() + "/search",
        {
            method: "GET",
            credentials: "same-origin",
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error: Received code: " + response.status)
            }

            return response.json();
        })
        .then(data => {
            removeChildren(venueSearchResult);

            for (let i in data.venues) {
                const result = document.createElement("option");
                result.value = data.venues[i]["uuid"];
                result.innerText = data.venues[i]["name"]
                    + " ("
                    + data.venues[i]["city"]
                    + ", "
                    + data.venues[i]["country"]
                    + ")";

                venueSearchResult.appendChild(result);
            }
        })
        .catch(error => {
            console.log(error);
        });
};


const event_VenueAddNewOpen = () => {
    const overlay = document.getElementById("new-venue");
    overlay.classList.add("active");
};


const event_VenueAddNewCancel = (event) => {
    const overlay = document.getElementById("new-venue");

    if (event.target.id === "new-venue" || event.target.id === "new-venue-cancel") {
        overlay.classList.remove("active");
    }
};


const event_VenueAddNewSubmit = () => {
    const overlay = document.getElementById("new-venue");
    const error = document.getElementById("new-venue-error");

    const venueName = document.getElementById("new-venue-name").value.trim();
    const venueAddress = document.getElementById("new-venue-address").value.trim();
    const venueCity = document.getElementById("new-venue-city").value.trim();
    const venuePostcode = document.getElementById("new-venue-postcode").value.trim();
    const venueState = document.getElementById("new-venue-state").value.trim();
    const venueCountry = document.getElementById("new-venue-country").value.trim();
    const venueGoogleUrl = document.getElementById("new-venue-googleurl").value.trim();
    const venueUrl = document.getElementById("new-venue-url").value.trim();

    if (venueName === "") {
        setPopUpError(error, "Name cannot be empty");
        return;
    }
    if (venueAddress === "") {
        setPopUpError(error, "Address cannot be empty");
        return;
    }
    if (venueCity === "") {
        setPopUpError(error, "City cannot be empty");
        return;
    }
    if (venuePostcode === "") {
        setPopUpError(error, "Post code cannot be empty");
        return;
    }
    if (venueState === "") {
        setPopUpError(error, "State/Region cannot be empty");
        return;
    }
    if (venueCountry === "") {
        setPopUpError(error, "Country cannot be empty");
        return;
    }
    if (venueCountry.length > 2) {
        setPopUpError(error, "Country must be two-letter code");
        return;
    }
    if (venueGoogleUrl === "") {
        setPopUpError(error, "Google Maps URL cannot be empty");
        return;
    }

    fetch("/api/director/shows/venue",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "same-origin",
            body: JSON.stringify(
                {
                    "name": venueName,
                    "address": venueAddress,
                    "city": venueCity,
                    "postcode": venuePostcode,
                    "state": venueState,
                    "country": venueCountry.toUpperCase(),
                    "google_url": venueGoogleUrl,
                    "venue_url": venueUrl === "" ? null : venueUrl,
                }
            ),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Received code: " + response.status + " " + response.statusText);
            }

            return response.json();
        })
        .then(data => {
            selectVenue(data["uuid"], data["name"]);
            overlay.classList.remove("active");
        })
        .catch(responseError => {
            setPopUpError(error, responseError);
        });
};


const event_VenueSelect = () => {
    const venueSearchResult = document.getElementById("venue-search-result");
    if (venueSearchResult.selectedIndex < 0) return;

    const selected = venueSearchResult.options[venueSearchResult.selectedIndex];

    selectVenue(selected.value, selected.innerText);
};


const event_ShowHideFestivalOnly = () => {
    const showTypeSelect = document.getElementById("type-select");
    const festivalSection = document.getElementById("festival-only");

    if (showTypeSelect.selectedIndex < 0) return;

    const selected = showTypeSelect.options[showTypeSelect.selectedIndex];

    if (selected.value === "CONCERT") {
        festivalSection.style.display = "none";
    } else {
        festivalSection.style.display = "block";
    }
};


const event_Submit = () => {
    // const bandSelected = [...document.getElementById("selected-bands").options].map(band => band.value);
    const bands = [...document.getElementById("selected-bands").options];

    const venueUuid = document.getElementById("selected-venue-uuid").value.trim();

    const eventType = document.getElementById("type-select").selectedOptions.item(0)?.value ?? null;
    const eventName = document.getElementById("event-name").value.trim();

    const startDate = document.getElementById("start-date").value;
    const startTime = document.getElementById("start-time").value;
    const timezone = document.getElementById("tz-select").selectedOptions.item(0)?.value ?? null;

    const festivalEndDate = document.getElementById("end-date").value;

    const bandInfo = {};

    if (eventType === "FESTIVAL") {
        bands.forEach(band => {
            let date = document.getElementById("band-" + band.value);

            if (!(date in bandInfo)) {
                bandInfo[date.value] = [];
            }

            bandInfo[date.value].push({ "band": band.value, "headliner": band.selected });
        });
    } else {
        bandInfo[startDate] = bands.map(band => {
            return {"band": band.value, "headliner": band.selected}
        });
    }

    const eventUrl = document.getElementById("event-url").value.trim();
    const imageFilename = document.getElementById("image-file").value.trim();
    const comments = document.getElementById("comments").value.trim();

    const submitData = {
        "type": eventType,
        "venue": venueUuid,
        "start_date": startDate,
        "start_time": startTime,
        "timezone": timezone,
        "schedule": Object.keys(bandInfo).map(date => {
            return {"date": date, "bands": bandInfo[date] }
        }),
    };

    if (eventName !== "") submitData["event_name"] = eventName;
    if (festivalEndDate !== "") submitData["end_date"] = festivalEndDate;
    if (eventUrl !== "") submitData["url"] = eventUrl;
    if (imageFilename !== "") submitData["image"] = imageFilename;
    if (comments !== "") submitData["comments"] = comments;

    console.log(JSON.stringify(submitData));

    fetch("/api/director/shows/event",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin",
            body: JSON.stringify(submitData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error: Received code: " + response.status)
            }

            return response.json();
        })
        .catch(error => {
            console.log(error);
        });
};


window.onload = () => {
    init();
};
