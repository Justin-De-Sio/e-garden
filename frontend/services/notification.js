export async function Notification(page, size) {
    const url = `http://localhost:8080/api/event/paginated?page=${page}&size=${size}`;

    try {

        const sessionCookie = useCookie("session");
        
        const token = sessionCookie.value;


        const response = await fetch(url, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*", 'Access-Control-Allow-Headers': '*',
                "Content-type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });
        console.log(response.ok)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log(data);
        console.log("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
        console.log(data.content);
        return data.content;
    } catch (error) {
        console.error('Error fetching data:', error);
    };
};