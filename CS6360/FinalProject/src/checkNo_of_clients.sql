ALTER TABLE Trainer
  ADD CONSTRAINT CheckNo_of_clients
  CHECK (No_of_clients <= 50);
