```mermaid
    classDiagram
        Semigroup <|-- Monoid
        
        Semigroupal <|-- Apply
        Functor <|-- Apply 
        
        Apply <|--Applicative
        Apply <|--FlatMap
        
        Applicative <|-- Monad
        FlatMap <|-- Monad
        
        Applicative <|-- ApplicativeError

        ApplicativeError <|-- MonadError
        Monad <|-- MonadError
```
