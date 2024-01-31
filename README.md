# Language Learning Backend

An app to store and learn words with the context sentence they were seen in. Designed to be used with Kindle vocab builder but can also work for words learned in other situations.

## API Reference

### Word Endpoints

| Endpoint                   | Method | Request                        | Response                           |
|----------------------------|--------|--------------------------------|------------------------------------|
| `/api/word`                | GET    | -                              | Array of Word objects              |
| `/api/word/{id}`           | GET    | `id` - ID of the word          | Word object                        |
| `/api/word/new`            | POST   | JSON representation of Word    | Added Word object                  |
| `/api/word/delete/{id}`    | DELETE | `id` - ID of the word to delete| Deleted Word object                |
| `/api/word/root/{id}`      | GET    | `id` - ID of the word          | RootWord object                    |

### RootWord Endpoints

| Endpoint                        | Method | Request                  | Response             |
|---------------------------------|--------|--------------------------|----------------------|
| `/api/root-word`                | GET    | -                        | Array of RootWord objects |
| `/api/root-word/delete/{id}`    | DELETE | `id` - ID of the root word | -                    |

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`OPENAI_API_KEY`

`DEEPL_API_KEY`

