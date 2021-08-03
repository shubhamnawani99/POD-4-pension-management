import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { RestrictAccessGuard } from './restrict-access.guard';

describe('RestrictAccessGuard', () => {
  let guard: RestrictAccessGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
    });
    guard = TestBed.inject(RestrictAccessGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
