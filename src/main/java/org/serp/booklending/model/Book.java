package org.serp.booklending.model;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {
  
    private String title;
    private String authorName;
    private String isbn; 
    private String summary;
    private String bookCover;

    private boolean archived;
    private boolean shareable;
    @ManyToOne
    @JoinColumn(name = "name_id")
    private User owner;
    
    @OneToMany(mappedBy ="book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy ="book")
    private List<BookTransactionHistory> histories;
 

    public  double getRate(){
        if (feedbacks==null || feedbacks.isEmpty()){
            return 0.0;
        }else {
            var rate= feedbacks.stream()
                    .mapToDouble(Feedback::getScore)
                    .average().orElse(0.0);
            return Math.round(rate*10.0)/10.0;
        }
    }
}
