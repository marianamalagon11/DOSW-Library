package edu.eci.dows.tdd.persistence.norelational.adapter;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.norelational.document.LoanEmbedded;
import edu.eci.dows.tdd.persistence.norelational.document.LoanRecordEmbedded;
import edu.eci.dows.tdd.persistence.norelational.document.UserDocument;
import edu.eci.dows.tdd.persistence.norelational.repository.UserMongoRepository;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class LoanRepositoryMongoAdapter implements LoanRepositoryPort {

    private final UserMongoRepository userRepo;

    public LoanRepositoryMongoAdapter(UserMongoRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Loan save(Loan loan) {
        String userId = loan.getUser().getId();

        UserDocument user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (user.getLoans() == null) user.setLoans(new ArrayList<>());

        LoanEmbedded embedded = toEmbedded(loan);

        List<LoanEmbedded> filtered = user.getLoans().stream()
                .filter(l -> !loan.getId().equals(l.getId()))
                .toList();

        user.setLoans(new ArrayList<>(filtered));
        user.getLoans().add(embedded);

        userRepo.save(user);
        return loan;
    }

    @Override
    public List<Loan> findAll() {
        return userRepo.findAll()
                .stream()
                .flatMap(u -> (u.getLoans() == null ? List.<LoanEmbedded>of() : u.getLoans()).stream()
                        .map(le -> toModel(le, u.getId())))
                .toList();
    }

    @Override
    public Optional<Loan> findById(String id) {
        return findAll().stream().filter(l -> id.equals(l.getId())).findFirst();
    }

    @Override
    public void deleteById(String id) {
        userRepo.findAll().forEach(u -> {
            if (u.getLoans() == null) return;

            List<LoanEmbedded> filtered = u.getLoans().stream()
                    .filter(l -> !id.equals(l.getId()))
                    .toList();

            if (filtered.size() != u.getLoans().size()) {
                u.setLoans(new ArrayList<>(filtered));
                userRepo.save(u);
            }
        });
    }

    @Override
    public long countActiveByBookId(String bookId) {
        List<String> returned = Arrays.asList("DEVUELTO", "RETURNED");
        return userRepo.findAll()
                .stream()
                .flatMap(u -> (u.getLoans() == null ? List.<LoanEmbedded>of() : u.getLoans()).stream())
                .filter(l -> bookId.equals(l.getBookId()))
                .filter(l -> l.getStatus() == null || returned.stream().noneMatch(s -> s.equalsIgnoreCase(l.getStatus())))
                .count();
    }

    @Override
    public List<Loan> findAllByUserId(String userId) {
        return userRepo.findById(userId)
                .map(u -> (u.getLoans() == null ? List.<LoanEmbedded>of() : u.getLoans()).stream()
                        .map(le -> toModel(le, userId))
                        .toList())
                .orElse(List.of());
    }

    @Override
    public boolean existsByIdAndUserId(String id, String userId) {
        return userRepo.findById(userId)
                .map(u -> (u.getLoans() == null ? List.<LoanEmbedded>of() : u.getLoans()).stream()
                        .anyMatch(l -> id.equals(l.getId())))
                .orElse(false);
    }

    private LoanEmbedded toEmbedded(Loan l) {
        LoanEmbedded le = new LoanEmbedded();
        le.setId(l.getId());
        le.setBookId(l.getBook().getId());
        le.setLoanDate(l.getLoanDate() == null ? null : l.getLoanDate().toString());
        le.setStatus(l.getStatus());
        le.setReturnDate(l.getReturnDate() == null ? null : l.getReturnDate().toString());

        List<LoanRecordEmbedded> record = null;
        if (l.getRecord() != null) {
            record = l.getRecord().stream()
                    .map(r -> {
                        LoanRecordEmbedded re = new LoanRecordEmbedded();
                        re.setStatus(r.getStatus());
                        re.setDate(r.getDate());
                        return re;
                    }).toList();
        } else {
            LoanRecordEmbedded re = new LoanRecordEmbedded();
            re.setStatus(l.getStatus());
            re.setDate(Instant.now().toString());
            record = List.of(re);
        }
        le.setRecord(record);

        return le;
    }

    private Loan toModel(LoanEmbedded le, String userId) {
        Book b = new Book();
        b.setId(le.getBookId());

        User u = new User();
        u.setId(userId);

        List<Loan.RecordEntry> record = null;
        if (le.getRecord() != null) {
            record = le.getRecord().stream()
                    .map(r -> new Loan.RecordEntry(r.getStatus(), r.getDate()))
                    .toList();
        }

        return new Loan(
                le.getId(),
                b,
                u,
                le.getLoanDate() == null ? null : LocalDate.parse(le.getLoanDate()),
                le.getStatus(),
                le.getReturnDate() == null ? null : LocalDate.parse(le.getReturnDate()),
                record
        );
    }
}