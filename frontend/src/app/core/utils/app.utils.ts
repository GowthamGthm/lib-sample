import { HttpHeaders } from "@angular/common/http";

export class AppUtils {


    static getHeaders(): HttpHeaders {
        let headers = new HttpHeaders();
        const value = localStorage.getItem("authToken");

        if (value) {
            headers.set("Authorization", value);
        }

        return headers;
    }


}