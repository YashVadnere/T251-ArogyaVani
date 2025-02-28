function fetchNearbyPlaces() {
  let targetLocation = { lat: 37.7749, lng: -122.4194 };

  let service = new google.maps.places.PlacesService(
    document.createElement("div")
  );

  let request = {
    location: targetLocation,
    radius: 5000,
    keyword: "stores",
  };

  service.nearbySearch(request, function (results, status) {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
      let placesList = document.getElementById("places-list");
      placesList.innerHTML = ""; // Clear previous results

      results.forEach((place) => {
        if (place.geometry && place.geometry.location) {
          let placeLatLng = {
            lat: place.geometry.location.lat(),
            lng: place.geometry.location.lng(),
          };

          let distance = getDistance(targetLocation, placeLatLng);

          let listItem = document.createElement("li");
          listItem.textContent = `${place.name} - ${distance.toFixed(
            2
          )} km away`;
          placesList.appendChild(listItem);
        }
      });
    } else {
      console.error("Places API request failed: " + status);
    }
  });
}

function getDistance(loc1, loc2) {
  const R = 6371;
  let dLat = toRad(loc2.lat - loc1.lat);
  let dLon = toRad(loc2.lng - loc1.lng);
  let a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRad(loc1.lat)) *
      Math.cos(toRad(loc2.lat)) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);
  let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c;
}

function toRad(deg) {
  return deg * (Math.PI / 180);
}
