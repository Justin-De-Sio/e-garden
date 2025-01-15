async function notification(page, size) {
    const url = `http://localhost:8080/api/event/paginated?page=${page}&size=${size}`;

    try {

        const sessionCookie = useCookie("session");
        console.log(sessionCookie);
        const token = sessionCookie.value;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log(data);
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}