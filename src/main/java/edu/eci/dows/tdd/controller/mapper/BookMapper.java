package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.core.model.Book;

public class BookMapper {

    public static Book toModel(BookDTO dto) {
        Book.Metadata md = null;
        if (dto.getMetadata() != null) {
            md = new Book.Metadata(dto.getMetadata().getPages(), dto.getMetadata().getLanguage(), dto.getMetadata().getCompany());
        }

        Book.Availability av = null;
        if (dto.getAvailability() != null) {
            av = new Book.Availability(
                    dto.getAvailability().getStatus(),
                    dto.getAvailability().getTotalCopies(),
                    dto.getAvailability().getAvailableCopies(),
                    dto.getAvailability().getBorrowedCopies()
            );
        }

        return new Book(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getTotalStock(),
                dto.getAvailableStock(),
                dto.getCategories(),
                dto.getPublicationType(),
                dto.getPublicationDate(),
                dto.getIsbn(),
                md,
                av,
                dto.getAddedAt()
        );
    }

    public static BookDTO toDTO(Book model) {
        BookDTO.MetadataDTO md = null;
        if (model.getMetadata() != null) {
            md = new BookDTO.MetadataDTO(model.getMetadata().getPages(), model.getMetadata().getLanguage(), model.getMetadata().getCompany());
        }

        BookDTO.AvailabilityDTO av = null;
        if (model.getAvailability() != null) {
            av = new BookDTO.AvailabilityDTO(
                    model.getAvailability().getStatus(),
                    model.getAvailability().getTotalCopies(),
                    model.getAvailability().getAvailableCopies(),
                    model.getAvailability().getBorrowedCopies()
            );
        }

        return new BookDTO(
                model.getId(),
                model.getTitle(),
                model.getAuthor(),
                model.getTotalStock(),
                model.getAvailableStock(),
                model.getCategories(),
                model.getPublicationType(),
                model.getPublicationDate(),
                model.getIsbn(),
                md,
                av,
                model.getAddedAt()
        );
    }
}