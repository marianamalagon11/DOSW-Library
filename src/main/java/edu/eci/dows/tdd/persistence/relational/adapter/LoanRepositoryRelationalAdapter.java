package edu.eci.dows.tdd.persistence.relational.adapter;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import edu.eci.dows.tdd.persistence.relational.entity.BookEntity;
import edu.eci.dows.tdd.persistence.relational.entity.LoanEntity;
import edu.eci.dows.tdd.persistence.relational.entity.UserEntity;
import edu.eci.dows.tdd.persistence.relational.repository.LoanRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
public class LoanRepositoryRelationalAdapter implements LoanRepositoryPort {

    private final LoanRepository repo;

    public LoanRepositoryRelationalAdapter(LoanRepository repo) {
        this.repo = repo;
    }

    @Override
    public Loan save(Loan loan) {
        LoanEntity e = new LoanEntity();
        e.setId(loan.getId());

        BookEntity be = new BookEntity();
        be.setId(loan.getBook().getId());
        e.setBook(be);

        UserEntity ue = new UserEntity();
        ue.setId(loan.getUser().getId());
        e.setUser(ue);

        e.setLoanDate(loan.getLoanDate());
        e.setStatus(loan.getStatus());
        e.setReturnDate(loan.getReturnDate());

        LoanEntity saved = repo.save(e);
        return toModel(saved);
    }

    @Override public List<Loan> findAll() { return repo.findAll().stream().map(this::toModel).toList(); }
    @Override public Optional<Loan> findById(String id) { return repo.findById(id).map(this::toModel); }
    @Override public void deleteById(String id) { repo.deleteById(id); }

    @Override
    public long countActiveByBookId(String bookId) {
        List<String> returned = Arrays.asList("DEVUELTO", "RETURNED");
        return repo.countByBookIdAndStatusNotIn(bookId, returned);
    }

    @Override
    public List<Loan> findAllByUserId(String userId) {
        return repo.findAllByUser_Id(userId).stream().map(this::toModel).toList();
    }

    @Override
    public boolean existsByIdAndUserId(String id, String userId) {
        return repo.existsByIdAndUser_Id(id, userId);
    }

    private Loan toModel(LoanEntity e) {
        Book b = new Book();
        b.setId(e.getBook().getId());
        User u = new User();
        u.setId(e.getUser().getId());

        return new Loan(
                e.getId(),
                b,
                u,
                e.getLoanDate(),
                e.getStatus(),
                e.getReturnDate(),
                null
        );
    }
}