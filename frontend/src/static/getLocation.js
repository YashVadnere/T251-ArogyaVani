async function getUserLocation() {
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        let location = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        };
        resolve(location);
      },
      (error) => {
        reject("Failed to get location");
      }
    );
  });
}

async function sendLocationToServer() {
  try {
    const location = await getUserLocation();

    const response = await fetch("https://your-api.com/location", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(location),
    });

    const result = await response.json();
    console.log("Server response:", result);
  } catch (error) {
    console.error("Error:", error);
  }
}

// Run this when the page loads
window.onload = sendLocationToServer;
