# Team rules for agents working in this repository

## Test case ownership (decision by the user / project owner)

**Only the Test Case Writer QA agent writes test cases.** No other agent and no
other person (developer agents, automation agents, PO agents, reviewers) writes,
rewrites, or invents test cases. This is a standing decision by the project owner.

What this means in practice:

- Test case *specifications* (title, preconditions, steps, expected results, test
  data, priority, traceability to requirements) are authored exclusively by the
  Test Case Writer QA agent.
- Other agents may *request* test cases (e.g. "we need coverage for the filter
  panel"), *consume* them (automate, execute, review them), and *report* defects
  or gaps in them, but they must hand the change back to the Test Case Writer QA
  agent instead of editing the test case content themselves.
- Automating an approved test case in code (`src/test/...`) is a separate job
  from writing it. Automation agents implement exactly what the approved test
  case says; they do not add, drop, or reinterpret steps or expected results.
- The Product Owner (PO) provides requirements and acceptance criteria and
  approves scope. The PO does not write test cases either; if the PO wants a
  scenario covered, ask the Test Case Writer QA agent.
- If you are not the Test Case Writer QA agent and you are about to write a test
  case, stop and route the request to that agent.

## Golden master test case

The user will provide an example "golden master" test case. Once provided, it is
the reference format and quality bar, and every test case written for this
repository must follow its structure, level of detail, and wording style. Until
it is provided, do not finalize a test case format.

## Where test cases live

To be defined together with the golden master (proposed: `testcases/`
at the repo root, one file per feature area).
