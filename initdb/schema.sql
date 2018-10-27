CREATE EXTENSION HSTORE;

DROP TABLE IF EXISTS public.journal;

CREATE TABLE IF NOT EXISTS public.journal (
  ordering BIGSERIAL,
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  deleted BOOLEAN DEFAULT FALSE,
  tags VARCHAR(255) DEFAULT NULL,
  message BYTEA NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);

CREATE UNIQUE INDEX journal_ordering_idx ON public.journal(ordering);

DROP TABLE IF EXISTS public.snapshot;

CREATE TABLE snapshot (
   "persistenceid" VARCHAR(254) NOT NULL,
   "sequencenr" INT NOT NULL,
   "timestamp" bigint NOT NULL,
   "snapshot" BYTEA,
   "manifest" VARCHAR(512),
   "json" JSON,
   CONSTRAINT "cc_snapshot_payload_json" check (snapshot IS NOT NULL OR (json IS NOT NULL AND manifest IS NOT NULL)),
   PRIMARY KEY (persistenceid, sequencenr)
);