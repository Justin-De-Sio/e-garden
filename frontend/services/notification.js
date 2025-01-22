

export async function Notification(page, size) {    
    const url = `http://localhost:8080/api/event/1`;    
    // const url = `http://localhost:8080/api/event/paginated?page=${page}&size=${size}`;    
    try {        
        const sessionCookie = useCookie("session");        
        const token = sessionCookie.value;               

        const response = await fetch(url, {           
            method: 'GET',           
            headers: {                
                "Content-Type": "application/json", 
                "Authorization": `Bearer ${token}`,           
            },
            credentials: 'include', // Pour inclure les cookies        
        });        

        if (!response.ok) {            
            throw new Error(`HTTP error! status: ${response.status}`);        
        }        

        const data = await response.json();        
        console.log(data);        
        return data;    
    } catch (error) {        
        console.error('Error fetching data:', error);        
        return null; // Retourner null en cas d'erreur    
    }
}
