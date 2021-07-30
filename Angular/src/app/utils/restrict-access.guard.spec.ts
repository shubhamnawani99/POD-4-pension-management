import { TestBed } from '@angular/core/testing';

import { RestrictAccessGuard } from './restrict-access.guard';

describe('RestrictAccessGuard', () => {
  let guard: RestrictAccessGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RestrictAccessGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
