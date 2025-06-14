
package com.library.management.dto;

import com.library.management.entity.Book;
import com.library.management.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookDto {


    private Long id;
    private UserDto user;
    private Book book;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private Boolean isReturned;


}